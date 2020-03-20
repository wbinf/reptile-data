package com.wbf.demo.task;

import com.wbf.demo.service.IJdItemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author: 魏斌锋
 * @Date: 2020/3/13 16:37
 * @Description:
 */
@Slf4j
@Component
public class ItemTask {
    @Autowired
    private IJdItemService jdItemService;
//    @Scheduled(fixedDelay = 100 * 1000)
    public  void itemTask(){
        String result = jdItemService.resolverData();
        if (StringUtils.equals("success",result)){
            log.info("爬取数据成功");
        }else {
            log.error("爬取数据失败");
        }
    }
}
