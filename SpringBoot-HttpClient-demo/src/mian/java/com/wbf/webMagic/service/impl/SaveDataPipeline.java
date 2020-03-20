package com.wbf.webMagic.service.impl;

import com.wbf.webMagic.entity.JobInfo;
import com.wbf.webMagic.service.IJobInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**保存数据到数据库
 * @Author: 魏斌锋
 * @Date: 2020/3/16 17:12
 * @Description:
 */

@Component
public class SaveDataPipeline implements Pipeline {
    @Autowired
    private IJobInfoService jobInfoService;
    @Override
    public void process(ResultItems resultItems, Task task) {
        JobInfo jobInfo = resultItems.get("jobInfo");
        if (jobInfo!=null){
           jobInfoService.selectJobInfoAndSave(jobInfo);
        }
    }
}
