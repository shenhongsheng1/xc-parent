package com.xc.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * @author ShenHongSheng
 * ClassName: CorsConfig
 * Description: 统一处理跨域配置类
 * Date: 2020/11/24 17:29
 * @version V1.0
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        //config.setAllowCredentials(Boolean.TRUE);   // 可选，用户是否可以发送、处理 cookie(是否允许携带cookies)；
        config.addAllowedMethod("*");      //允许的http方法
        config.addAllowedOrigin("*");      //允许的请求源
        config.addAllowedHeader("*");      //允许的请求头
        //config.addExposedHeader("Authorization:");  // 配置前端js允许访问的自定义响应头
        //config.setExposedHeaders(new ArrayList<String>());   //可选，可以让用户拿到的字段(返回的响应头)。有几个字段无论设置与否都可以拿到的，包括：Cache-Control、Content-Language、Content-Type、Expires、Last-Modified、Pragma 。
        config.setMaxAge(600L);            // 预请求的存活有效期
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }
}
