package com.wbf.webMagic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wbf.webMagic.entity.AgencyIp;

/**
 * <p>
 * 免费代理IP表 服务类
 * </p>
 *
 * @author 魏斌锋
 * @since 2020-03-17
 */
public interface IAgencyIpService extends IService< AgencyIp > {
    /**
     * 根据ip地址和端口号  判断是否新增或修改数据
     * @param agencyIp
     * @return
     */
    boolean selectAgencyIpInfo(AgencyIp agencyIp);
}
