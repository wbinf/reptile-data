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
 * 招聘信息
 * </p>
 *
 * @author 魏斌锋
 * @since 2020-03-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tbl_job_info")
public class JobInfo extends Model< JobInfo > {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 公司联系方式
     */
    private String companyAddr;

    /**
     * 公司信息
     */
    private String companyInfo;

    /**
     * 职位名称
     */
    private String jobName;

    /**
     * 工作地点
     */
    private String jobAddr;

    /**
     * 职位信息
     */
    private String jobInfo;

    /**
     * 薪资范围，最小
     */
    private Integer salaryMin;

    /**
     * 薪资范围，最大
     */
    private Integer salaryMax;

    /**
     * 招聘信息详情页
     */
    private String url;

    /**
     * 职位最近发布时间
     */
    private String time;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
