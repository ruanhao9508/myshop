package com.qf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auth RuanHao
 * @Time 2019/12/6 17:42
 **/
@Controller
@RequestMapping("/search")
public class SearchController {

    @RequestMapping("/keyword")
    public String searchByKeyword(String keyword){
        System.out.println("关键字:"+keyword);
        return  null;
    }
}
