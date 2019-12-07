package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.service.ISearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Auth RuanHao
 * @Time 2019/12/6 17:42
 **/
@Controller
@RequestMapping("/search")
public class SearchController {

    @Reference
    private ISearchService searchService;

    /**
     * 根据关键字搜索内容
     * @param keyword
     * @return
     */
    @RequestMapping("/keyword")
    public String searchByKeyword(String keyword, Model model){
        System.out.println("关键字:"+keyword);
        List<Goods> goodsList = searchService.searchSolr(keyword);
        model.addAttribute("goodsList",goodsList);
        return  "searchresult";
    }
}
