package com.xc.manage.controller;

import com.xc.common.component.RestHelper;
import com.xc.common.domain.ResultInfo;
import com.xc.common.domain.UserVO;
import com.xc.manage.api.AuthClient;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;

/**
 * @author ShenHongSheng
 * ClassName: UserController
 * Description:
 * Date: 2020/12/8 9:24
 * @version V1.0
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AuthClient authClient;

    @Autowired
    private RestHelper restHelper;

    @Autowired
    @Qualifier("inner")
    private RestTemplate innerRestTemplate;     //调用内部服务接口，通过（http:// + 注册的服务名称 + 接口url）调用

    @Autowired
    @Qualifier("outer")
    private RestTemplate outerRestTemplate;    //调用外部服务接口，通过（http:// + ip + 端口 + 接口url）调用

    /**
     * 根据用户名获取用户信息
     * @author ShenHongSheng
     * version: 2020/12/7
     * @return : null
     */
    @GetMapping(value = "/getUserInfo")
    @ResponseBody
    public ResultInfo<UserVO> getUserInfoByFeignClient(HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        if (userName == null) {
            throw new AuthenticationException("用户名为空，请进行登录！");
        }
        LOGGER.info("FeignClient调用xc-service-auth服务的getUserInfo接口入参: ", userName);
        ResultInfo<UserVO> userInfo = authClient.getUserInfo(userName);
        if (userInfo == null) {
            LOGGER.error("FeignClient调用xc-service-auth服务的getUserInfo接口失败！");
            throw new RuntimeException("FeignClient调用xc-service-auth服务的getUserInfo接口失败！");
        }
        return userInfo;
    }

    @GetMapping(value = "/getUserInfoByInnerRestTemplate")
    @ResponseBody
    public ResultInfo<UserVO> getUserInfoByInnerRestTemplate(HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        if (userName == null) {
            throw new AuthenticationException("用户名为空，请进行登录！");
        }
        LOGGER.info("RestTemplate调用xc-service-auth服务的getUserInfo接口入参: ", userName);
        String url = "http://xc-service-auth/auth/user/getUserInfo?userName=" + userName;
        ResponseEntity<ResultInfo> resultBody = innerRestTemplate.getForEntity(url, ResultInfo.class);
        if (resultBody.getBody() == null) {
            LOGGER.error("RestTemplate调用xc-service-auth服务的getUserInfo接口失败！");
            throw new RuntimeException("RestTemplate调用xc-service-auth服务的getUserInfo接口失败！");
        }
        return resultBody.getBody();
    }

    @GetMapping(value = "/getUserInfoByOuterRestTemplate")
    @ResponseBody
    public ResultInfo<UserVO> getUserInfoByOuterRestTemplate(HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        if (userName == null) {
            throw new AuthenticationException("用户名为空，请进行登录！");
        }
        LOGGER.info("RestTemplate调用xc-service-auth服务的getUserInfo接口入参: ", userName);
        ParameterizedTypeReference<ResultInfo<UserVO>> resultType =
                new ParameterizedTypeReference<ResultInfo<UserVO>>(){};
        String url = "http://localhost:8001/auth/user/getUserInfo?userName=" + userName;
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<ResultInfo<UserVO>> resultBody = outerRestTemplate.exchange(url, HttpMethod.GET, request, resultType);
        if (resultBody.getBody() == null) {
            LOGGER.error("RestTemplate调用xc-service-auth服务的getUserInfo接口失败！");
            throw new RuntimeException("RestTemplate调用xc-service-auth服务的getUserInfo接口失败！");
        }
        return resultBody.getBody();
    }

    @GetMapping(value = "/getUserInfoByRestHelper")
    @ResponseBody
    public ResultInfo<UserVO> getUserInfoByRestHelper(HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        if (userName == null) {
            throw new AuthenticationException("用户名为空，请进行登录！");
        }
        LOGGER.info("RestTemplate调用xc-service-auth服务的getUserInfo接口入参: ", userName);
        ParameterizedTypeReference<ResultInfo<UserVO>> resultType = new ParameterizedTypeReference<ResultInfo<UserVO>>(){};
        String url = "http://xc-service-auth/auth/user/getUserInfo?userName=" + userName;
        ResultInfo<UserVO> resultBody = restHelper.getForEntityFromInner(url, HttpMethod.GET, resultType);
        if (resultBody == null) {
            LOGGER.error("RestTemplate调用xc-service-auth服务的getUserInfo接口失败！");
            throw new RuntimeException("RestTemplate调用xc-service-auth服务的getUserInfo接口失败！");
        }
        return resultBody;
    }
}
