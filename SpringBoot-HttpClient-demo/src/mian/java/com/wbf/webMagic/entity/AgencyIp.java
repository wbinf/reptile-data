package com.wbf.webMagic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 免费代理IP表
 * </p>
 *
 * @author 魏斌锋
 * @since 2020-03-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tbl_agency_ip")
public class AgencyIp extends Model< AgencyIp > {

    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * ip端口
     */
    private Integer ipPort;

    /**
     * 服务器地址
     */
    private String serverAddress;

    /**
     * 验证时间
     */
    private String proofTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }


}
