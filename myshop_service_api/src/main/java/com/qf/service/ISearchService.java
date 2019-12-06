package com.qf.service;

import com.qf.entity.Goods;

import java.util.List;

/**
 * @Auth RuanHao
 * @Time 2019/12/6 19:33
 **/
public interface ISearchService {

    /**
     * 添加到索引库
     * @param goods
     * @return
     */
    int insertSolr(Goods goods);

    /**
     * 根据关键字查询索引库
     * @param keyword
     * @return
     */
    List<Goods> searchSolr(String keyword);

}
