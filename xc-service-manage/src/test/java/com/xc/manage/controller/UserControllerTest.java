package com.xc.manage.controller;

import com.alibaba.fastjson.JSONObject;
import com.xc.common.component.RestHelper;
import com.xc.common.domain.ResultInfo;
import com.xc.common.domain.UserVO;
import com.xc.common.enums.GlobalStatusEnum;
import com.xc.manage.XcServiceManageApplication;
import com.xc.manage.api.AuthClient;
import com.xc.manage.helper.PasswordHelper;
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

import java.util.Date;

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

    @Autowired
    private AuthClient authClient;

    @Autowired
    private PasswordHelper passwordHelper;

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

    /**
     * 添加用户
     * @author ShenHongSheng
     * version: 2020/12/21
     * @return : ResultInfo<String>
     */
    @Test
    public void addUserTest() {
        UserVO userVO = new UserVO();
        userVO.setUserName("731603");
        userVO.setName("N731603");
        userVO.setPassword("oa731603#");
        userVO.setSalt("123456");
        userVO.setStatus((byte) 1);
        userVO.setCreateTime(new Date());
        userVO.setUpdateTime(new Date());
        passwordHelper.encryptPassword(userVO);
        ResultInfo<String> resultInfo = authClient.addUser(userVO);
        if (!GlobalStatusEnum.SUCCESS.getCode().equals(resultInfo.getCode())) {
            throw new RuntimeException(resultInfo.getMessage());
        }
        LOGGER.info("RestTemplate调用xc-service-auth服务的addUser接口成功：{}", resultInfo.getResult());
    }
}
