package com.qf.myshop_searchservice;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.qf")
@DubboComponentScan("com.qf.service.impl")
public class MyshopSearchserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyshopSearchserviceApplication.class, args);
    }

}
