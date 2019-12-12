package com.qf.myshop_messages;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyshopMessagesApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void Messages(){
        String phone="13147009508";
        String code=(int)(Math.random()*900000+100000)+"";

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FkapwacSxuMxmwycTBH", "vCSmOveAQr4xTeMa271qkCD5lzMcHU");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers",phone);
        request.putQueryParameter("SignName", "扶摇直上九万里");
        request.putQueryParameter("TemplateCode", "SMS_180057903");
        request.putQueryParameter("TemplateParam", "{code:"+code+"}");
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
