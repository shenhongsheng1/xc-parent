package com.xc.web.controller;

import com.xc.common.component.RestHelper;
import com.xc.common.domain.ResultInfo;
import com.xc.common.domain.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ShenHongSheng
 * ClassName: webController
 * Description:
 * Date: 2021/2/7 17:59
 * @version V1.0
 */
@RestController
@RequestMapping(value = "/user")
public class webController {

    private static final Logger LOGGER = LoggerFactory.getLogger(webController.class);

    @Autowired
    private RestHelper restHelper;

    /**
     * 根据用户名获取用户信息
     * @author ShenHongSheng
     * version: 2020/12/7
     * @return : null
     */
    @GetMapping(value = "/getUserInfo")
    @ResponseBody
    public ResultInfo<UserVO> getUserInfo(String userName) {
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
