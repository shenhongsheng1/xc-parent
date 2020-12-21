package com.xc.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = {"com.xc.manage", "com.xc.common.component"})
@EnableFeignClients
public class XcServiceManageApplication {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Bean
    @Qualifier("inner")
    @LoadBalanced        //开启负载均衡的功能
    public RestTemplate innerRestTemplate() {
        return this.restTemplateBuilder.build();
    }

    @Bean
    @Qualifier("outer")
    public RestTemplate outerRestTemplate() {
        return this.restTemplateBuilder.build();
    }

    public static void main(String[] args) {
        SpringApplication.run(XcServiceManageApplication.class, args);
    }
}
