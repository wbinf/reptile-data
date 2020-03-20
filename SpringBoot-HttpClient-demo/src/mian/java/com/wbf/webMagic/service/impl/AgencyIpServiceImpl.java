package com.wbf.webMagic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wbf.webMagic.entity.AgencyIp;
import com.wbf.webMagic.mapper.AgencyIpMapper;
import com.wbf.webMagic.service.IAgencyIpService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 免费代理IP表 服务实现类
 * </p>
 *
 * @author 魏斌锋
 * @since 2020-03-17
 */
@Service
public class AgencyIpServiceImpl extends ServiceImpl< AgencyIpMapper, AgencyIp > implements IAgencyIpService {
@Resource
private AgencyIpMapper agencyIpMapper;

    @Override
    public boolean selectAgencyIpInfo(AgencyIp agencyIp) {
        LambdaQueryWrapper < AgencyIp > objectLambdaQueryWrapper = new LambdaQueryWrapper <>();
        objectLambdaQueryWrapper.eq(AgencyIp::getIpAddress,agencyIp.getIpAddress()).eq(AgencyIp::getIpPort,agencyIp.getIpPort());
        AgencyIp selectOne = agencyIpMapper.selectOne(objectLambdaQueryWrapper);
        if (selectOne!=null){
            return false;
        }
      return this.saveOrUpdate(agencyIp);
    }
}