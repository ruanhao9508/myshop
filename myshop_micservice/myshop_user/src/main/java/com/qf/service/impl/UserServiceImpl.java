package com.qf.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.UserMapper;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Auth RuanHao
 * @Time 2019/12/10 21:39
 **/
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    public int register(User user) {

        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",user.getUsername());
        //查这个username的数量
        Integer count = userMapper.selectCount(queryWrapper);
        //为0表示这个数据库username没有注册,则进行注册
        if(count == 0){
            return userMapper.insert(user);
        }
        //该用户已存在
        return -1;
    }

    /**
     * 邮箱找回时,输入用户名,根据用户名查找用户信息
     * @param username
     * @return
     */
    @Override
    public User getUserByName(String username) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",username);

        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    /**
     * 修改密码
     * @param username
     * @param password
     * @return
     */
    @Override
    public int updatePassword(String username, String password) {
        //调用方法查到用户信息
        User user = this.getUserByName(username);
        //设置新的密码
        user.setPassword(password);
        //修改密码
        return userMapper.updateById(user);
    }

    /**
     * 手机验证码找回时,根据手机号查找用户信息
     * @param phone
     * @return
     */
    @Override
    public User getUserByPhone(String phone) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("phone",phone);

        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    /**
     * 手机验证码修改密码
     * @param phone
     * @param password
     * @return
     */
    @Override
    public int updateMMByPhone(String phone, String password) {
        User user = this.getUserByPhone(phone);
        user.setPassword(password);
        return userMapper.updateById(user);
    }
}
