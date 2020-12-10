package com.xc.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class XcServiceAuthApplication {

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
        SpringApplication.run(XcServiceAuthApplication.class, args);
    }

}
