package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qf.entity.Goods;

import java.util.List;

/**
 * @Auth RuanHao
 * @Time 2019/12/3 17:09
 **/
public interface GoodsMapper  extends BaseMapper<Goods> {
    /**
     * 查询所有商品信息
     * @return
     */
   List<Goods> getList();

    /**
     * 分页查询
     * @param page
     * @return
     */
    IPage<Goods> getListPage(Page<Goods> page);
}
