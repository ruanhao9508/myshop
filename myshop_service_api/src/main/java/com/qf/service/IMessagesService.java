package com.qf.service;

import com.qf.entity.Messages;

/**
 * @Auth RuanHao
 * @Time 2019/12/11 17:21
 **/
public interface IMessagesService {

    /**
     * 发送验证码
     * @param messages
     * @return
     */
    void sendMessages(Messages messages);
}
