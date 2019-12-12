package com.qf.myshop_mail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

@SpringBootTest
class MyshopMailApplicationTests {

    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    void contextLoads() throws MessagingException {

        //创建一封邮件
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        //设置邮件内容,直接用mimeMessage(java原生的api)设置内容不太好用,建以使用以下
        //spring提供的一个便捷的邮件设置对象 MimeMessageHelper,multipart true 表示有附件
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);

        /*设置内容*/
        //设置标题
        mimeMessageHelper.setSubject("会员到期通知");

        //发送方
        mimeMessageHelper.setFrom("ruanhao9508@sina.com");

        //接收方,三种,角色状态的不同
        mimeMessageHelper.setTo("845378084@qq.com");//接受者,直接(目的)参与人,知道抄送的存在
//        mimeMessageHelper.setCc();//抄送,直接目的不是这个,但他知道这封邮件
//        mimeMessageHelper.setBcc();//密送,接收者和抄送都不知这个人的存在

        //内容 后面true表示可以发送html
        mimeMessageHelper.setText("会员已到期,请及时续费!<img src='http://img0.imgtn.bdimg.com/it/u=2242212773,2792770847&fm=26&gp=0.jpg'/>",true);

        //发送附件
        mimeMessageHelper.addAttachment("我的附件.jpg",new File("C:\\Users\\86131\\Desktop\\TestImage\\1000.jpeg"));

        //设置时间
        mimeMessageHelper.setSentDate(new Date());

        //发送邮件
        javaMailSender.send(mimeMessage);

        System.out.println("邮件发送成功!");

    }


}
