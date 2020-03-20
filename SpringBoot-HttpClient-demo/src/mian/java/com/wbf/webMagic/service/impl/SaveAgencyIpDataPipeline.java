package com.wbf.webMagic.service.impl;

import com.wbf.webMagic.entity.AgencyIp;
import com.wbf.webMagic.service.IAgencyIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * @Author: 魏斌锋
 * @Date: 2020/3/17 17:14
 * @Description:
 */
@Component
public class SaveAgencyIpDataPipeline implements Pipeline {
    @Autowired
    private IAgencyIpService agencyIpService;
    @Override
    public void process(ResultItems resultItems, Task task) {

        List <AgencyIp> agencyIpList = resultItems.get("agencyIp");
        agencyIpList.forEach(agencyIp->{
            agencyIpService.selectAgencyIpInfo(agencyIp);
        });

        }

}
