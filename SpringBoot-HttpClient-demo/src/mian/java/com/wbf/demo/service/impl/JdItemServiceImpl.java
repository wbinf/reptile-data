package com.wbf.demo.service.impl;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wbf.demo.entity.JdItem;
import com.wbf.demo.mapper.JdItemMapper;
import com.wbf.demo.service.IJdItemService;
import com.wbf.demo.utils.HttpClientUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 京东商品表 服务实现类
 * </p>
 *
 * @author 魏斌锋
 * @since 2020-03-13
 */
@Service
public class JdItemServiceImpl extends ServiceImpl < JdItemMapper, JdItem > implements IJdItemService {
    @Resource
    private JdItemMapper jdItemMapper;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public String resolverData() {
//声明需要解析的初始地址
        String url = "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&suggest=1.his.0.0&s=121&click=0&page=";

        //按照页面对手机的搜索结果进行遍历解析
        for (int i = 1; i < 10; i = i + 2) {
            String html = HttpClientUtils.getHtml(url + i);
            //解析页面，获取商品数据并存储
            this.parse(html);
        }
        return "success";
    }
    @Override
    public List < JdItem > selectAllwithSku(Long sku) {
        LambdaQueryWrapper < JdItem > objectLambdaQueryWrapper = new LambdaQueryWrapper <>();
        objectLambdaQueryWrapper.eq(JdItem::getSku, sku);
        return jdItemMapper.selectList(objectLambdaQueryWrapper);
    }

    //解析页面，获取商品数据并存储
    private void parse(String html) {
        Document document = Jsoup.parse(html);
        //获取spu信息
        Elements elements = document.select("div#J_goodsList >ul >li");
        System.out.println("elements"+elements);
        List<JdItem> list=new ArrayList <>();
        elements.forEach(element -> {
            //获取spu
            long spu = Long.parseLong(element.attr("data-spu"));
            //获取sku信息
            Elements skuList = element.select("li.ps-item");
            for  (Element elementSkus:skuList) {
                JdItem jdItem = new JdItem();
                //设置商品的spu
                jdItem.setSpu(spu);
                long sku = Long.parseLong(elementSkus.select("[data-sku]").attr("data-sku"));
                List < JdItem > jdItemList = this.selectAllwithSku(sku);
                if (jdItemList!=null&&jdItemList.size()>0){
                    //如果商品存在，就进行下一个循环，该商品不保存，因为已存在
                    continue;
                }
                //设置商品的sku
                jdItem.setSku(sku);
                //获取商品的详情的url
                String itemUrl = "https://item.jd.com/" + sku + ".html";
                jdItem.setUrl(itemUrl);
                //获取商品的图片
                String picUrl = "https:" + elementSkus.select("img[data-sku]").first().attr("data-lazy-img");
                picUrl = picUrl.replace("/n9/", "/n1/");
                try {
                    String picName = HttpClientUtils.downImage(picUrl);
                    jdItem.setPic(picName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //获取商品的价格
                String priceJson = HttpClientUtils.getHtml("https://p.3.cn/prices/mgets?skuIds=J_" + sku);
                double price = 0;
                try {
                    price = MAPPER.readTree(priceJson).get(0).get("p").asDouble();
                    jdItem.setPrice(price);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                //获取商品的标题
                String detailHtml = HttpClientUtils.getHtml(itemUrl);
                String title = Jsoup.parse(detailHtml).select("div.sku-name").text();
                jdItem.setTitle(title);
                list.add(jdItem);
            }

        });
        // 批量保存商品数据到数据库中
        if (list!=null&&list.size()>0){
            this.saveBatch(list);
        }
    }
}
