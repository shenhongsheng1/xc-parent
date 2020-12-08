package com.xc.manage.controller;

import com.xc.common.domain.ResultInfo;
import com.xc.common.domain.UserVO;
import com.xc.manage.api.AuthClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ShenHongSheng
 * ClassName: UserController
 * Description:
 * Date: 2020/12/8 9:24
 * @version V1.0
 */
@RestController(value = "/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AuthClient authClient;

    /**
     * 根据用户名获取用户信息
     * @author ShenHongSheng
     * version: 2020/12/7
     * @param userName :
     * @return : null
     */
    @GetMapping(value = "/getUserInfo")
    @ResponseBody
    public ResultInfo<UserVO> getUserInfo(@RequestParam String userName) {
        LOGGER.info("FeignClient调用xc-service-auth服务的getUserInfo接口入参: ", userName);
        ResultInfo<UserVO> userInfo = authClient.getUserInfo(userName);
        if (userInfo == null) {
            LOGGER.error("FeignClient调用xc-service-auth服务的getUserInfo接口失败！");
            throw new RuntimeException("FeignClient调用xc-service-auth服务的getUserInfo接口失败！");
        }
        return userInfo;
    }
}
