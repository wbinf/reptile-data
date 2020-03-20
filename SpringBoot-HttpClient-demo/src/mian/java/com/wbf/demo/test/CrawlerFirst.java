package com.wbf.demo.test;

import com.alibaba.druid.util.HttpClientUtils;
import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.builder.HCB;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import org.apache.http.client.HttpClient;

/**
 * @Author: 魏斌锋
 * @Date: 2020/3/13 10:21
 * @Description:
 */
public class CrawlerFirst {
    public static void test1(){
        //1 打开浏览器 输入网址
        String url="http://www.baidu.com";
        HttpConfig config = HttpConfig.custom().url(url).encoding("utf-8").timeout(5000);
        //2发起请求，返回响应   解析响应 获取数据
        try {
            String result = HttpClientUtil.get(config);
            System.out.println("返回百度的HTML"+"\n"+result);
        } catch (HttpProcessException e) {
            e.printStackTrace();
        }
    }
    //使用连接池
    public static void test2() throws HttpProcessException {
        //插件式配置生成HttpClient时所需参数（超时、连接池）
        HCB hcb=HCB.custom()
                .timeout(5000)
                .pool(100,10);
        HttpClient client = hcb.build();
        HttpConfig config = HttpConfig.custom()
                .client(client)
                .url("http://www.baidu.com")
                .encoding("utf-8");
        String result = HttpClientUtil.get(config);
        System.out.println("返回百度的HTML"+"\n"+result);

    }

    public static void main(String[] args) {

        try {
            test2();
        } catch (HttpProcessException e) {
            e.printStackTrace();
        }
    }
}
