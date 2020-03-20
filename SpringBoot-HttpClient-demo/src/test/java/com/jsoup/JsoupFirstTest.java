package com.jsoup;

import com.wbf.MainApplication;
import com.wbf.demo.service.IJdItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: 魏斌锋
 * @Date: 2020/3/13 11:14
 * @Description:
 */


@SpringBootTest(classes = MainApplication.class)
public class JsoupFirstTest {
    @Autowired
    private IJdItemService jdItemService;
    //使用dom获取数据
    @Test
    public void testUrl() throws Exception{
//        String url="https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&suggest=1.his.0.0&s=121&click=0&page=1";
//        //解析url地址
//        Header[] headers = HttpHeader.custom().userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36").build();
//        String html = HttpClientUtil.get(HttpConfig.custom().url(url).encoding("utf-8").headers(headers));
//        System.out.println("66"+html);
//        Document document = Jsoup.parse(html);
//        //使用标签选择器，获取剃头两天标签中的内容
//        Elements elements = document.select("div#J_goodsList >ul >li");
//       // String title = document.getElementsByTag("title").text();
//        System.out.println("elements:"+elements);
////        Element elementById = document.getElementById("使用Redis的有序集合实现排行榜功能");
////        System.out.println("elementById:"+elementById.text());
        jdItemService.resolverData();
    }
}
