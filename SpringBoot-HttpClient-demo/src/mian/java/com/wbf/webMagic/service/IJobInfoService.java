package com.wbf.webMagic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wbf.webMagic.entity.JobInfo;

import java.util.List;

/**
 * <p>
 * 招聘信息 服务类
 * </p>
 *
 * @author 魏斌锋
 * @since 2020-03-16
 */
public interface IJobInfoService extends IService< JobInfo > {
    /**
     * 根据详情页url和发布时间 判断是否新增或修改数据
     * @param jobInfo
     * @return
     */
    boolean selectJobInfoAndSave(JobInfo jobInfo);
}
