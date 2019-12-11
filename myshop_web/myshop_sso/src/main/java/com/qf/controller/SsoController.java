package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auth RuanHao
 * @Time 2019/12/10 21:01
 **/
@Controller
@RequestMapping("/sso")
public class SsoController {

    @Reference
    private IUserService userService;

    @RequestMapping("/tologin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/toregister")
    public String toRegister(){
        return "register";
    }

    /**
     * 用户注册
     * @return
     */
    @RequestMapping("/register")
    public String register(User user, Model model){
        int result = userService.register(user);

        if(result > 0){
            //注册成功
            return "login";
        }else if(result == -1){
            //用户名已存在
            model.addAttribute("error","用户明已存在!");
        }else{
            model.addAttribute("error","注册失败!");
        }
        //失败回到注册页面
        return "register";
    }
}