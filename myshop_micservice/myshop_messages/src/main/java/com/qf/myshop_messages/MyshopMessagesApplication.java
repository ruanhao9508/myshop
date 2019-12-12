package com.qf.myshop_messages;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.qf")
@DubboComponentScan("com.qf.service.impl")
public class MyshopMessagesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyshopMessagesApplication.class, args);
    }

}
