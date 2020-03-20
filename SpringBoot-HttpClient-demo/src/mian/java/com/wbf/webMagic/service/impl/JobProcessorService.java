package com.wbf.webMagic.service.impl;

import com.wbf.webMagic.entity.JobInfo;
import com.wbf.webMagic.utils.MathSalary;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * @Author: 魏斌锋
 * @Date: 2020/3/16 15:09
 * @Description:
 */

@Component
public class JobProcessorService implements PageProcessor {
    @Autowired
    private SaveDataPipeline saveDataPipeline;
    private String url = "https://search.51job.com/list/000000,000000,0000,01%252C32,9,99,java,2,1.html?lang=c&stype=&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&providesalary=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=";

    private Site site = Site.me()
            .setCharset("gbk")
            .setTimeOut(10 * 1000)
            .setRetrySleepTime(3000)
            .setRetryTimes(3);

    @Override
    public Site getSite() {
        return site;
    }

    /**
     * 解析页面
     * @param page page
     */

    @Override
    public void process(Page page) {
        //解析页面，获取招聘信息详情的url地址
        List < Selectable > list = page.getHtml().css("div#resultList div.el").nodes();
        //判断获取到的集合列表是否为空
        // 不为空表示列表页

        if (list != null && list.size() > 0) {
            //获取列表地址
            list.forEach(selectable -> {
                String jobInfoUrl = selectable.links().toString();
                //把获取到的url地址放到任务队列中
                page.addTargetRequest(jobInfoUrl);
            });
            //获取下一页的url
            //获取翻页按钮的超链接
            List<String> bkUrl = page.getHtml().$("div.p_in li.bk").links().all();
            page.addTargetRequests(bkUrl);

        } // 为空表示招聘详情列  获取招聘详细信息 并保存数据
        else {
            this.saveJobInfo(page);
        }


    }

    private void saveJobInfo(Page page) {
        Html html = page.getHtml();
        JobInfo jobInfo = new JobInfo();
        //公司名称
        jobInfo.setCompanyName(html.css("div.cn p.cname a", "text").toString());

        //公司地址
        jobInfo.setCompanyAddr(html.$("div.tBorderTop_box:nth-child(2) p.fp", "text").toString());
        //公司信息
        jobInfo.setCompanyInfo(html.$("div.tmsg", "text").toString());
        //职位名称
        jobInfo.setJobName(html.$("div.cn h1", "text").toString());
        //工作地点
        jobInfo.setJobAddr(html.$("div.tBorderTop_box:nth-child(2) p.fp", "text").toString());
        //职位信息
        jobInfo.setJobInfo(Jsoup.parse(html.$("div.job_msg").toString()).text());
        //工资范围 换算成年薪
        String salaryStr = html.$("div.cn strong", "text").toString();
        jobInfo.setSalaryMin(MathSalary.getSalary(salaryStr)[0]);
        jobInfo.setSalaryMax(MathSalary.getSalary(salaryStr)[1]);
        //职位详情url
        jobInfo.setUrl(page.getUrl().toString());
        //职位发布时间
        String time = html.$("div.cn p.msg", "text").regex("[0-9]{1,2}-[0-9]{1,2}发布").toString();

        jobInfo.setTime(time.substring(0,time.length()-2));

       //保存数据
        page.putField("jobInfo", jobInfo);
    }



    /**
     * 启动
     */
  //  @Scheduled(initialDelay = 1000,fixedDelay = 100*1000)
    public void process(){
        Spider.create(new JobProcessorService())
                .addUrl(url)
//                .setScheduler(new QueueScheduler()
//                        //设置布隆过滤器
//                        .setDuplicateRemover(new BloomFilterDuplicateRemover(10000000)))
                .thread(5)
                .addPipeline(saveDataPipeline)
                .run();
    }


}
