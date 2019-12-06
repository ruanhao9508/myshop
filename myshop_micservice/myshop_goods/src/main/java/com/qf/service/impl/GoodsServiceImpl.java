package com.qf.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qf.dao.GoodsImagesMapper;
import com.qf.dao.GoodsMapper;
import com.qf.entity.Goods;
import com.qf.entity.GoodsImages;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auth RuanHao
 * @Time 2019/12/3 17:08
 **/
@Service
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsImagesMapper goodsImagesMapper;

    /**
     * 查询所有商品信息
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Goods> list() {
        //查询所有商品信息
        List<Goods> goodsList = goodsMapper.getList();
        return goodsList;
    }

    /**
     * 添加商品信息
     * @param goods
     * @return
     */
    @Override
    @Transactional
    public int insert(Goods goods) {
        //保存商品的信息,mybatisplus insert方法已经实现主键回填了
        goodsMapper.insert(goods);

        //保存商品封面图片信息
        GoodsImages goodsFmImages=new GoodsImages().setGid(goods.getId()).setUrl(goods.getFmImage()).setIsfengmian(1);
        goodsImagesMapper.insert(goodsFmImages);

        //保存商品其他图片信息
        for(String otherImage:goods.getOtherImages()){
            GoodsImages goodsOtherImages=new GoodsImages().setGid(goods.getId()).setUrl(otherImage).setIsfengmian(0);
            goodsImagesMapper.insert(goodsOtherImages);
        }

        return 1;
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    @Override
    public IPage<Goods> listPage(Page<Goods> page) {
        return goodsMapper.getListPage(page);
    }
}
