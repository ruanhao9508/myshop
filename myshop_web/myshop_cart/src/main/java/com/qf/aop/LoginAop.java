package com.qf.aop;

import com.qf.entity.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;

/**
 * @Auth RuanHao
 * @Time 2019/12/13 17:39
 **/
@Aspect//表示这是个切面
@Component//被扫到
public class LoginAop {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 增强方法 (切点表达式)
     *  切点表达式 - 表示增强那些指定的方法
     * @return
     */
    @Around("@annotation(IsLogin)")//增强加了@IsLogin注解的方法
    public Object isLogin(ProceedingJoinPoint proceedingJoinPoint){

        //判断登录状态
        //1.获得cookie

        //获得登录的loginToken
        String loginToken=null;

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("loginToken")){
                    loginToken = cookie.getValue();
                    break;
                }
            }
        }
        //判断是否登录
        User user=null;
        if (loginToken != null){
            //2.通过cookie访问redis
            user = (User) redisTemplate.opsForValue().get(loginToken);
        }

        //3.拿到登录信息(要么为空,要么非空)
        if (user == null){
            //  3.1-为空(未登录)
            //     3.1.1-判断@IsLogin(mustlogin = true) ->强制跳转到登录页
            //获得IsLogin注解
            MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
            Method method = methodSignature.getMethod();//代表当前增强方法的反射对象
            IsLogin isLogin = method.getAnnotation(IsLogin.class);
            boolean flag = isLogin.mustLogin();

            if(flag){
                //当前页面的url
                //
                String currentPageUrl = request.getRequestURL().toString()+"?"+request.getQueryString();
                //编码
                try {
                    currentPageUrl = URLEncoder.encode(currentPageUrl, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //前置跳转到登录页面
                String loginUrl="http://localhost:8082/sso/tologin?currentPageUrl="+currentPageUrl;
                return "redirect:"+loginUrl;
            }
        }

        //  3.2非空(已登录)
        //     3.2.1->保存登录信息,让开发者能在controller中拿到
        UserHolder.setUser(user);

        //调用目标方法
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        //清空threadlocal,保证下次拿到这个线程里面的值为空
        UserHolder.setUser(null);
        return result;
    }
}
