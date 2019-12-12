package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Email;
import com.qf.entity.Messages;
import com.qf.entity.ResultData;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Auth RuanHao
 * @Time 2019/12/11 0:06
 **/
@Controller
@RequestMapping("/forget")
public class ForgetController {

    @Reference
    private IUserService userService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired//不序列化
    private StringRedisTemplate redisTemplate;

    /**
     * 跳转到忘记密码页面
     * @return
     */
    @RequestMapping("/toforgetPassword")
    public String toForgetPassword(){
        return "forgetpassword";
    }

    /**
     * 发送邮件找回密码
     * @return
     */
    @RequestMapping("/sendmail")
    @ResponseBody
    public ResultData<Map<String,String>> sendMailPassword(String username){
        //通过用户名找到用户信息
        User user = userService.getUserByName(username);

        if (user == null){
            //用户名不存在
            return new ResultData<Map<String,String>>().setCode(ResultData.ResultCodeList.ERROR).setMsg("该用户不存在!");
        }

        //生成UUID
        String uuid= UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(uuid,username);
        //设置超时时间,5分钟
        redisTemplate.expire(uuid,5, TimeUnit.MINUTES);
        //找回密码的链接 1.一次性 2.时效性 3.只能改自己密码
        String url="http://localhost:8082/forget/toUpdatePassword?token="+uuid;

        //发送邮件
        Email email=new Email()
                .setTo(user.getEmail())
                .setSubject("找回密码.非本人请忽略!")
                .setContext("点击<a href='"+url+"'>这里</a>找回密码")
                .setSendTime(new Date());

        //异步发送邮件
        rabbitTemplate.convertAndSend("direct_exchange","email",email);

        //拿到邮箱
        String emailName= user.getEmail();
        //邮箱的截取并替换,传到前台显示,提示用户用的那个邮箱
        String mStr=emailName.substring(3,emailName.lastIndexOf("@"));
        String showEmail = emailName.replace(mStr, "********");
        //点击去到邮箱
        String toEmail="mail."+emailName.substring(emailName.lastIndexOf("@")+1);
        //前台要显示的数据
        Map<String,String> map=new HashMap<>();
        map.put("showemail",showEmail);
        map.put("toemail",toEmail);

        return new ResultData<Map<String,String>>().setCode(ResultData.ResultCodeList.OK).setMsg("邮件发送成功").setData(map);
    }

    /**
     * 跳转到修改密码页面
     * @return
     */
    @RequestMapping("/toUpdatePassword")
    public String toUpdatePassword(){
        return "toupdatepassword";
    }

    /**
     * 修改密码
     * @param newpassword
     * @param token
     * @return
     */
    @RequestMapping("/updatepassword")
    public String updatepassword(String newpassword,String token){

        //校验token的有效性
        String username = redisTemplate.opsForValue().get(token);
        if (username == null){
            //token失效或者没有值
            return "updateerror";
        }

        //token有效,修改密码
        userService.updatePassword(username,newpassword);
        //删除token
        redisTemplate.delete(token);
        return "login";
    }

    /**
     * 发送短信验证码
     * @param messages
     * @return
     */
    @RequestMapping("/sendmsg")
    @ResponseBody
    public String sendMessages(Messages messages){

        //生成6位数验证码
        String code=(int)(Math.random()*900000+100000)+"";
        messages.setCode(code);
//        System.out.println(messages);
        redisTemplate.opsForValue().set("Code",code);
//        String code1 = redisTemplate.opsForValue().get("Code");
//        System.out.println(code1);
        //设置超时时间,5分钟
        redisTemplate.expire("Code",5, TimeUnit.MINUTES);
        //异步发送短信验证码
        rabbitTemplate.convertAndSend("direct_exchange","messages",messages);
        String data="发送成功!";
        return data;
    }

    /**
     * 用户点击找回密码,验证输入的手机号和验证码,并根据手机号查询用户的存在性
     * @param messages
     * @return
     */
    @RequestMapping("/getuserbyphone")
    @ResponseBody
    public ResultData getUerbyPhone(Messages messages){
        ResultData resultData=new ResultData();
        String code = redisTemplate.opsForValue().get("Code");
        if (messages.getCode().equals(code)){
            //说明验证码相同,查询用户存在与否
            User user = userService.getUserByPhone(messages.getPhone());
//            System.out.println(user);
            if (user!=null){
                String url="/forget/toupdatemm?phone="+messages.getPhone();
                resultData.setCode(ResultData.ResultCodeList.OK).setData(url);
                return resultData;
            }
            //用户不存在
            resultData.setCode(ResultData.ResultCodeList.ERROR).setMsg("该用户不存在或手机号有误!");
        }else{//验证码错误
            resultData.setCode(ResultData.ResultCodeList.ERROR).setMsg("验证码错误");
        }
        return resultData;
    }

    /**
     * 跳转到修改密码页面
     * @return
     */
    @RequestMapping("/toupdatemm")
    public String toUpdateMM(){
        return "updatemmbyphone";
    }


    /**
     * 修改密码
     * @param newpassword
     * @param phone
     * @return
     */
    @RequestMapping("/updatemmbyphone")
    public String updateMMByPhone(String newpassword,String phone){
        //修改密码
        userService.updateMMByPhone(phone,newpassword);
        //删除token
        redisTemplate.delete("Code");
        return "login";
    }
}
