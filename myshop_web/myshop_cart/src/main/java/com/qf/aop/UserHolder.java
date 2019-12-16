package com.qf.aop;

import com.qf.entity.User;

/**
 * @Auth RuanHao
 * @Time 2019/12/13 18:57
 **/
public class UserHolder {

    //那个线程放的那个线程才可以拿
    private static ThreadLocal<User> user = new ThreadLocal<>();

    public static boolean isLogin(){
        return user != null;
    }

    public static void setUser(User user){
        UserHolder.user.set(user);
    }

    public static User getUser(){
        return user.get();
    }
}
