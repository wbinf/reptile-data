package com.wbf.webMagic.service.impl;

import com.wbf.webMagic.entity.AgencyIp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 魏斌锋
 * @Date: 2020/3/17 15:59
 * @Description:
 */
@Component
public class AgencyIpProcessorService implements PageProcessor {
    @Autowired
    private SaveAgencyIpDataPipeline saveAgencyIpDataPipeline;

    @Override
    public void process(Page page) {

        List < String > result = page.getHtml().xpath("//table[@id=ip_list]/tbody/").all();
        List < AgencyIp > agencyIpList = new ArrayList <>();
        for (int i = 3; i <= result.size(); i++) {
            //text:	[, 121.33.220.158, 808, 广东广州, 高匿, HTTPS, 504天, 22分钟前]
            List < String > all = page.getHtml().xpath("//table[@id=ip_list]/tbody/" + "tr[" + i + "]/td/text()").all();
            if (all != null && all.size() > 0) {
                AgencyIp agencyIp = new AgencyIp();
                agencyIp.setIpAddress(all.get(1));
                agencyIp.setIpPort(Integer.parseInt(all.get(2)));
                agencyIp.setServerAddress(all.get(3));
                agencyIp.setProofTime(all.get(all.size() - 1));
                agencyIpList.add(agencyIp);
            }
        }
        page.putField("agencyIp", agencyIpList);

    }


    private Site site = Site.me()
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
            .setCharset("utf-8")
            .setTimeOut(10 * 1000)
            .setRetrySleepTime(3000)
            .setRetryTimes(3);

    @Override
    public Site getSite() {
        return site;
    }

   @Scheduled(initialDelay = 1000, fixedDelay = 100 * 1000)
    public void process() {
        String url = "https://www.xicidaili.com/";
        Spider.create(new AgencyIpProcessorService())
                .addUrl(url)
                .thread(5)
                .addPipeline(saveAgencyIpDataPipeline)
                .run();

    }


}
