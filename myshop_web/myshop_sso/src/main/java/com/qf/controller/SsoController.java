package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.qf.entity.ResultData;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Auth RuanHao
 * @Time 2019/12/10 21:01
 **/
@Controller
@RequestMapping("/sso")
public class SsoController {

    @Reference
    private IUserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

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

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public String login(String username, String password, String currentPageUrl, HttpServletResponse response){
        //根据用户名查询用户信息
        User user = userService.getUserByName(username);

        //结果不为空用户存在,判断密码是否一致,一致跳转首页,不一致跳转登录页,提示用户名或密码有误
        if (user != null && user.getPassword().equals(password)){
            //存储登录信息(保存登录状态),以便后面判断用户是否登录
            String token = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(token,user);
            redisTemplate.expire(token,7, TimeUnit.DAYS);

            //将token写入浏览器cookie中
            Cookie cookie = new Cookie("loginToken",token);
            cookie.setMaxAge(60*60*24*7);//单位是秒
            cookie.setPath("/");//路径
            cookie.setDomain("localhost");//域名 可以设置一级域名,不能设置为顶级域名(com)
//            cookie.setHttpOnly(true);//只能服务器读取cookie,页面上的js脚本不能读取修改cookie
//            cookie.setSecure(true);//只有http协议下,服务器才会收到cookie


            response.addCookie(cookie);//响应时写入浏览器(response)
            if (currentPageUrl == null || currentPageUrl.equals("")){
                currentPageUrl = "http://localhost";
            }
            //跳转到首页
            return "redirect:"+currentPageUrl;
        }
        //查询出结果为空,则用户不存在,跳转值登录页面并提示用户
        String error = "用户名或密码有误";
        try {
            error = URLEncoder.encode(error,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "redirect:/sso/tologin?error="+error;
    }

    /**
     * 判断是否登录
     * 请求跨域 JSONP解决方法
     * servlet的拿法:拿到cookie(logintoken),在请求体中(headers中),用request请求拿
     * springMVC的拿法:@CookieValue(name = "cookie的key",required = false) String loginToken
     *                  required = false(是否必须,不写默认true,如果没有这个cookie就会报错)
     * @param loginToken
     * @return
     */
    @RequestMapping("/islogin")
    @ResponseBody
    public String isLogin(@CookieValue(name = "loginToken",required = false) String loginToken, String callback){

        System.out.println("获得客户端的loginToken:"+loginToken);
        ResultData<User> resultData = new ResultData<User>().setCode(ResultData.ResultCodeList.ERROR).setData(null);
        if(loginToken != null){
            //获取redis中的用户信息
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if(user != null){
                resultData.setCode(ResultData.ResultCodeList.OK).setData(user);
            }
        }
        //这样写既能用jsonp也可以用普通ajax
        return callback != null ? callback+"("+ JSON.toJSONString(resultData)+")" : JSON.toJSONString(resultData);
    }

    /**
     * 后台springMVC 解决请求跨越问题
     * 前台正常ajax使用
     * @param loginToken
     * @return
     */
   /* @CrossOrigin(allowCredentials = "true")
    @RequestMapping("/islogin")
    @ResponseBody
    public ResultData<User> isLogin(@CookieValue(name = "loginToken",required = false) String loginToken){

        System.out.println("获得客户端的loginToken:"+loginToken);
        ResultData<User> resultData = new ResultData<User>().setCode(ResultData.ResultCodeList.OK).setData(null);
        //这样写既能用jsonp也可以用普通ajax
        return resultData;
    }*/

    /**
     * 注销登录
     * @return
     */
   @RequestMapping("/loginout")
   public String loginOut(@CookieValue(name = "loginToken",required = false) String loginToken,HttpServletResponse response){
       System.out.println("注销登录:"+loginToken);
       redisTemplate.delete(loginToken);

       //删除cookie
       Cookie cookie = new Cookie("loginToken",null);
       cookie.setMaxAge(0);//单位是秒
       cookie.setPath("/");//路径
       cookie.setDomain("localhost");
       response.addCookie(cookie);
       return "redirect:/sso/tologin";
   }
}