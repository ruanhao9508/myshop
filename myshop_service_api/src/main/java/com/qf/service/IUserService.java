package com.qf.service;

import com.qf.entity.User;

/**
 * @Auth RuanHao
 * @Time 2019/12/10 21:32
 **/
public interface IUserService {

    /**
     * 用户注册
     * @param user
     * @return
     */
    int register(User user);

    /**
     * 根据用户名查找用户信息
     * @param username
     * @return
     */
    User getUserByName(String username);

    /**
     * 修改密码
     * @param username
     * @param password
     * @return
     */
    int updatePassword(String username,String password);

    /**
     * 根据手机号查找用户信息
     * @param phone
     * @return
     */
    User getUserByPhone(String phone);

    /**
     * 手机验证码修改密码
     * @param phone
     * @param password
     * @return
     */
    int updateMMByPhone(String phone,String password);
}
