package com.qf.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.qf.entity.Messages;
import com.qf.service.IMessagesService;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * @Auth RuanHao
 * @Time 2019/12/11 16:47
 **/
@Service
public class sendMessages implements IMessagesService {

    /**
     * 发送验证码
     * @param messages
     * @return
     */
    @RabbitListener(queuesToDeclare = @Queue(name = "messages_queue"))
    @Override
    public void sendMessages(Messages messages) {
        //生成6位数验证码
//        String code=(int)(Math.random()*900000+100000)+"";

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FkapwacSxuMxmwycTBH", "vCSmOveAQr4xTeMa271qkCD5lzMcHU");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers",messages.getPhone());
        request.putQueryParameter("SignName", "扶摇直上九万里");
        request.putQueryParameter("TemplateCode", "SMS_180057903");
        request.putQueryParameter("TemplateParam", "{code:"+messages.getCode()+"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
