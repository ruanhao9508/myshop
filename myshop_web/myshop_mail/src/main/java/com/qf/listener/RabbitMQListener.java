package com.qf.listener;

import com.qf.entity.Email;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @Auth RuanHao
 * @Time 2019/12/11 1:58
 **/
@Component
public class RabbitMQListener {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @RabbitListener(queuesToDeclare = @Queue(name = "mail_queue"))
    public void msgHandler(Email email) throws MessagingException {

        //创建一封邮件
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        //设置邮件内容,直接用mimeMessage(java原生的api)设置内容不太好用,建以使用以下
        //spring提供的一个便捷的邮件设置对象 MimeMessageHelper,multipart true 表示有附件
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);

        /*设置内容*/
        //设置标题
        mimeMessageHelper.setSubject(email.getSubject());

        //发送方
        mimeMessageHelper.setFrom(from);

        //接收方,三种,角色状态的不同
        mimeMessageHelper.setTo(email.getTo());//接受者,直接(目的)参与人,知道抄送的存在
//        mimeMessageHelper.setCc();//抄送,直接目的不是这个,但他知道这封邮件
//        mimeMessageHelper.setBcc();//密送,接收者和抄送都不知这个人的存在

        //内容 后面true表示可以发送html
        mimeMessageHelper.setText(email.getContext(),true);

        //发送附件
//        mimeMessageHelper.addAttachment("我的附件.jpg",new File("C:\\Users\\86131\\Desktop\\TestImage\\1000.jpeg"));

        //设置时间
        mimeMessageHelper.setSentDate(email.getSendTime());

        //发送邮件
        javaMailSender.send(mimeMessage);

        System.out.println("邮件发送成功!");
    }
}
