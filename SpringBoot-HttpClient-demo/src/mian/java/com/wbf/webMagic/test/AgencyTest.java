package com.wbf.webMagic.test;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * @Author: 魏斌锋
 * @Date: 2020/3/17 14:28
 * @Description:
 */
public class AgencyTest implements PageProcessor {
    @Override
    public void process(Page page) {
        List < String > all = page.getHtml().$("div.well p").all();
        page.putField("text",all);

    }

    @Override
    public Site getSite() {
        return Site.me().setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
    }

    public static void main(String[] args) {
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("121.33.220.158",808)));
        Spider.create(new AgencyTest())
                .addUrl("https://www.ip.cn/")
                .setDownloader(httpClientDownloader)
                .run();
    }
}