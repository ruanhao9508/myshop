package com.qf.myshop_back;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "com.qf")
@Import(FdfsClientConfig.class)
public class MyshopBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyshopBackApplication.class, args);
    }

}
