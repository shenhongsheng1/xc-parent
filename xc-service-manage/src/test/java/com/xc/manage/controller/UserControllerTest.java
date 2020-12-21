package com.xc.manage.controller;

import com.alibaba.fastjson.JSONObject;
import com.xc.common.component.RestHelper;
import com.xc.common.domain.ResultInfo;
import com.xc.common.domain.UserVO;
import com.xc.common.enums.GlobalStatusEnum;
import com.xc.manage.XcServiceManageApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ShenHongSheng
 * ClassName: UserController
 * Description:
 * Date: 2020/12/21 10:10
 * @version V1.0
 */
@SpringBootTest(classes = {XcServiceManageApplication.class})
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@Transactional
@Rollback
public class UserControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserControllerTest.class);

    @Autowired
    private RestHelper restHelper;

    @Test
    public void getUserInfoByRestHelperTest() {
        String userName = "731603";
        ParameterizedTypeReference<ResultInfo<UserVO>> resultType = new ParameterizedTypeReference<ResultInfo<UserVO>>(){};
        String url = "http://xc-service-auth/auth/user/getUserInfo?userName=" + userName;
        ResultInfo<UserVO> resultBody = restHelper.getForEntityFromInner(url, HttpMethod.GET, resultType);
        if (!GlobalStatusEnum.SUCCESS.getCode().equals(resultBody.getCode())) {
            throw new RuntimeException("RestTemplate调用xc-service-auth服务的getUserInfo接口失败！");
        }
        LOGGER.info("RestTemplate调用xc-service-auth服务的getUserInfo接口成功：{}", JSONObject.toJSON(resultBody));
    }
}
