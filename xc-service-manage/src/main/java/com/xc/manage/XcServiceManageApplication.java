package com.xc.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class XcServiceManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(XcServiceManageApplication.class, args);
    }

}
