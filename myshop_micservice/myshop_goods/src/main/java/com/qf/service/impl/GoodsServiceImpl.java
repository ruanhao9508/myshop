package com.qf.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.GoodsMapper;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Auth RuanHao
 * @Time 2019/12/3 17:08
 **/
@Service
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 查询所有商品信息
     * @return
     */
    @Override
    public List<Goods> list() {
        List<Goods> goodsList = goodsMapper.selectList(null);
        return goodsList;
    }
}
