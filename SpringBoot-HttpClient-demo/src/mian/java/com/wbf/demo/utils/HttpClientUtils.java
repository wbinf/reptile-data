package com.wbf.demo.utils;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.builder.HCB;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @Author: 魏斌锋
 * @Date: 2020/3/13 17:06
 * @Description:
 */
public class HttpClientUtils {
    public static String getHtml(String url) {
        HCB hcb = null;
        try {
            hcb = HCB.custom().pool(100, 10);
        } catch (HttpProcessException e) {
            e.printStackTrace();
        }
        HttpClient client = hcb.build();
        Header[] headers = HttpHeader.custom().userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36").build();
        HttpConfig config = HttpConfig.custom().headers(headers).url(url).encoding("utf-8").client(client);
        try {
            return HttpClientUtil.get(config);
        } catch (HttpProcessException e) {
            e.printStackTrace();
        }
        return "failed";
    }

    /**
     * 下载图片并返回图片名
     *
     * @param url 图片路径
     * @return
     * @throws Exception
     */
    public static String downImage(String url) throws Exception {
        HCB hcb = HCB.custom().pool(100, 10);
        Header[] headers = HttpHeader.custom().userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36").build();
        //下载图片
        //获取图片的后缀

        String extName = url.substring(url.lastIndexOf("."));
        //创建图片名，重命名图片
        String picName = UUID.randomUUID().toString() + extName;
        //下载图片
        //声明OutPutStream
        OutputStream outputStream = new FileOutputStream(new File("E:\\download\\image\\" +
                picName));
        HttpConfig config = HttpConfig.custom().headers(headers).url(url).encoding("utf-8").out(outputStream);
        HttpClientUtil.down(config);
        return picName;
    }
}