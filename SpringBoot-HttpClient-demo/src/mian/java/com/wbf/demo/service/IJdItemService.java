package com.wbf.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wbf.demo.entity.JdItem;

import java.util.List;

/**
 * <p>
 * 京东商品表 服务类
 * </p>
 *
 * @author 魏斌锋
 * @since 2020-03-13
 */
public interface IJdItemService extends IService<JdItem> {
    /**
     * 爬取京东数据
     * @return
     */
    String resolverData();

    /**
     * 根据sku 查询数据
     * @param sku
     * @return
     */
    List<JdItem> selectAllwithSku(Long sku);
}
