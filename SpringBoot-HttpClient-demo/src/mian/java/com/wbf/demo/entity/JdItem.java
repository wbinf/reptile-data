package com.wbf.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 京东商品表
 * </p>
 *
 * @author 魏斌锋
 * @since 2020-03-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tbl_jd_item")
public class JdItem extends Model<JdItem> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品集合id
     */
    private Long spu;

    /**
     * 商品最小品类单元id
     */
    private Long sku;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 商品价格
     */
    private Double price;

    /**
     * 商品图片
     */
    private String pic;

    /**
     * 商品详情地址
     */
    private String url;

    /**
     * 创建时间
     */
    private LocalDateTime created;

    /**
     * 更新时间
     */
    private LocalDateTime updated;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
