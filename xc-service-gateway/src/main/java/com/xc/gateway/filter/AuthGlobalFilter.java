package com.xc.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.xc.common.domain.ResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ShenHongSheng
 * ClassName: AuthGlobaFilter
 * Description: 全局Filter，统一处理登录与外部不允许访问的服务
 * Date: 2020/11/25 10:03
 * @version V1.0
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private Logger logger = LoggerFactory.getLogger(AuthGlobalFilter.class);

//    用来做类URLs字符串匹配，可以做URLs匹配，规则如下
//          ？匹配一个字符
//          *匹配0个或多个字符
//          **匹配0个或多个目录
//    用例如下:
//          /trip/api/*x    匹配 /trip/api/x，/trip/api/ax，/trip/api/abx ；但不匹配 /trip/abc/x；
//          /trip/a/a?x    匹配 /trip/a/abx；但不匹配 /trip/a/ax，/trip/a/abcx
//          /**/api/alie    匹配 /trip/api/alie，/trip/dax/api/alie；但不匹配 /trip/a/api
//          /**/*.htmlm   匹配所有以.htmlm结尾的路径
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("============ 拦截器鉴权开始 ===========");
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        logger.info("============ 请求path：{} ===========", path);
        //api接口，校验用户必须登录
        if(!antPathMatcher.match("/**/**", path)) {
            return authFailure(exchange);
            /*List<String> tokenList = request.getHeaders().get("token");   校验请求头中的token
            if(null == tokenList) {
                ServerHttpResponse response = exchange.getResponse();
                return out(response);
            } else {
                Boolean isCheck = JwtUtils.checkToken(tokenList.get(0));
                if(!isCheck) {
                    ServerHttpResponse response = exchange.getResponse();
                    return out(response);
                }
            }*/
        }
        return chain.filter(exchange);
    }

    public int getOrder() {
        return 0;
    }

    /**
     * 检验失败
     * @author ShenHongSheng
     * version: 2020/11/25
     * @param exchange :
     */
    private Mono<Void> authFailure(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        Map<String, Object> detailedMessage = new HashMap<>();
        detailedMessage.put("path", request.getURI().getPath());
        detailedMessage.put("message", "gateway拦截器url权限检验失败！");
        ResultInfo<String> resultInfo =
                new ResultInfo<>("401", "鉴权失败", detailedMessage, "fail");
        byte[] bits = JSONObject.toJSONString(resultInfo).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }
}
