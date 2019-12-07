package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auth RuanHao
 * @Time 2019/12/7 15:04
 **/
@Controller
@RequestMapping("/goodsDetails")
public class goodsDetailsController {

    @Reference
    private IGoodsService goodsService;

    @RequestMapping("/details")
    public String getGoodsById(Integer id, Model model){
        Goods goods = goodsService.getGoodsById(id);
        model.addAttribute("goods",goods);
        return "goodsdetails";
    }
}
