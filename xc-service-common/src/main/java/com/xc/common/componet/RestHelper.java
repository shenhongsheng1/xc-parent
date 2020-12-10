package com.xc.common.componet;

import com.alibaba.nacos.common.utils.MapUtils;
import com.xc.common.exception.InnerInterfaceException;
import com.xc.common.exception.OuterInterfaceException;
import com.xc.common.utils.ObjectUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author ShenHongSheng
 * ClassName: RestHelper
 * Description:
 * Date: 2020/12/10 17:14
 * @version V1.0
 */
@Component
public class RestHelper {

    @Autowired
    @Qualifier("inner")
    private RestTemplate innerRestTemplate;

    @Autowired
    @Qualifier("outer")
    private RestTemplate outerRestTemplate;


    /**
     * 访问内部REST服务接口的GET方法.
     * @author ShenHongSheng
     * @param url           请求url
     * @param httpMethod   请求方法
     * @param resultType   返回结果类型
     * @param uriVariables 查询字符串中uri占位符的值形如name={1}
     * @param <T>           返回结果泛型
     * @return T
     */
    public <T> T getForEntityFromInner(String url, HttpMethod httpMethod, ParameterizedTypeReference<T> resultType, Object... uriVariables) {
        return this.getForEntity(this.innerRestTemplate, url, httpMethod, null, resultType, false, uriVariables);
    }

    /**
     * @author ShenHongSheng
     * version: 2020/12/10
     * @param restTemplate : restTemplate服务（inner内部，outer外部）
     * @param url : 请求url
     * @param httpMethod : 请求方法
     * @param header : 请求头
     * @param resultType : 返回结果类型
     * @param isOuter : 是否转json
     * @param uriVariables : 查询字符串中uri占位符的值形如name={1}
     * @param <T>           返回结果泛型
     * @return : T
     */
    private <T> T getForEntity(RestTemplate restTemplate, String url, HttpMethod httpMethod, Map<String, String> header, ParameterizedTypeReference<T> resultType, boolean isOuter, Object... uriVariables) {
        Validate.isTrue(httpMethod == HttpMethod.GET, "HttpMethod不是GET...");
        if (ObjectUtils.isNullOrEmpty(resultType)) {
            resultType = new ParameterizedTypeReference<T>() {
                @Override
                public Type getType() {
                    return super.getType();
                }
            };
        }
        // 配置Http header
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        if (MapUtils.isNotEmpty(header)) {
            httpHeaders.setAll(header);
        }
        HttpEntity<?> request = new HttpEntity<>(httpHeaders);
        return getResponseBody(restTemplate, url, httpMethod, request, isOuter, resultType, uriVariables);
    }

    /**
     * @author ShenHongSheng
     * version: 2020/12/10
     * @param restTemplate : restTemplate服务（inner内部，outer外部）
     * @param url : 请求url
     * @param httpMethod : 请求方法
     * @param request : 请求头
     * @param isOuter : 是否转json
     * @param resultType : 返回结果类型
     * @param uriVariables : 查询字符串中uri占位符的值形如name={1}
     * @param <T>           返回结果泛型
     * @return : T
     */
    private <T> T getResponseBody(RestTemplate restTemplate, String url, HttpMethod httpMethod, HttpEntity<?> request, boolean isOuter, ParameterizedTypeReference<T> resultType, Object[] uriVariables) {
        // 调用RestTemplate请求方法
        ResponseEntity<T> responseEntity = null;
        if (httpMethod == HttpMethod.POST) {
            responseEntity = restTemplate.exchange(url, httpMethod, request, resultType);
        }else if (httpMethod == HttpMethod.PUT || httpMethod == HttpMethod.DELETE) {
            restTemplate.exchange(url, httpMethod, request, String.class);
        }else if (httpMethod == HttpMethod.GET) {
            responseEntity = restTemplate.exchange(url, httpMethod, request, resultType, uriVariables);
        }
        int statusCode = 0;
        T body = null;
        if (null != responseEntity) {
            statusCode = responseEntity.getStatusCodeValue();
            body = responseEntity.getBody();
        }
        if (statusCode < HttpStatus.MULTIPLE_CHOICES.value()) {
            return body;
        } else {
            if (isOuter) {
                throw new OuterInterfaceException(String.format("访问%s出错.错误码是%s.", url, statusCode));
            } else {
                throw new InnerInterfaceException(String.format("访问%s出错.错误码是%s.", url, statusCode));
            }
        }
    }
}
