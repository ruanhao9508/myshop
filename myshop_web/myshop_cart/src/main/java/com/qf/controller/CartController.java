package com.qf.controller;

import com.qf.aop.IsLogin;
import com.qf.aop.UserHolder;
import com.qf.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auth RuanHao
 * @Time 2019/12/13 16:50
 **/
@Controller
@RequestMapping("/cart")
public class CartController {

    /**
     * 添加购物车
     * @param goodsid
     * @param goodsnumber
     * @return
     */
    @IsLogin(mustLogin = true)
    @RequestMapping("/insert")
    public String insert(Integer goodsid,Integer goodsnumber){
        System.out.println("加入购物车,商品id:"+goodsid+"-----数量:"+goodsnumber);

        User user = UserHolder.getUser();

        System.out.println(user);
        //登录认证
        return "addcart";
    }
}
