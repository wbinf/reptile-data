package com.wbf.webMagic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wbf.webMagic.entity.JobInfo;
import com.wbf.webMagic.mapper.JobInfoMapper;
import com.wbf.webMagic.service.IJobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 招聘信息 服务实现类
 * </p>
 *
 * @author 魏斌锋
 * @since 2020-03-16
 */
@Service
public class JobInfoServiceImpl extends ServiceImpl< JobInfoMapper, JobInfo > implements IJobInfoService{
@Resource
private JobInfoMapper jobInfoMapper;

    @Override
    public boolean selectJobInfoAndSave(JobInfo jobInfo) {
        LambdaQueryWrapper < JobInfo > objectLambdaQueryWrapper = new LambdaQueryWrapper <>();
        objectLambdaQueryWrapper.eq(JobInfo::getUrl,jobInfo.getUrl()).eq(JobInfo::getTime,jobInfo.getTime());
        JobInfo jobInfoResult = jobInfoMapper.selectOne(objectLambdaQueryWrapper);
        if (jobInfoResult!=null){
            return false;
        }

        return this.saveOrUpdate(jobInfo);
    }
}
