package com.wbf.webMagic.test;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * @Author: 魏斌锋
 * @Date: 2020/3/14 14:47
 * @Description:
 */

public class JobProcessor implements PageProcessor {
    /**
     * 该方法负责解析页面
     *
     * @param page page
     */
    @Override
    public void process(Page page) {
        //css
        List < String > all = page.getHtml().css("div#navitems ul a").all();
        page.putField("div",all );
        //获取超链接下的标题
      page.addTargetRequests( page.getHtml().css("div#navitems ul a").links().all());
       page.putField("link",page.getHtml().css("div#channel"));
    }
    private Site site = Site.me()
            .setCharset("utf-8") //设置编码
            .setTimeOut(10000)    //超时时间
            .setRetrySleepTime(5000) //重试的间隔时间
            .setRetryTimes(3);  //重试的次数
    @Override
    public Site getSite() {

        return site;
    }

    public static void main(String[] args) {
        Spider.create(new JobProcessor())
                .addUrl("https://www.jd.com/")
                .thread(5)
                .run();
    }
}
