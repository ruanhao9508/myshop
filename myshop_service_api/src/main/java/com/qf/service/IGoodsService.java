package com.qf.service;

import com.qf.entity.Goods;

import java.util.List;

/**
 * @Auth RuanHao
 * @Time 2019/12/3 17:03
 **/
public interface IGoodsService {

    /**
     * 查询所有商品信息
     * @return
     */
    List<Goods> list();

    /**
     * 添加商品信息
     * @param goods
     * @return
     */
    int insert(Goods goods);
}
