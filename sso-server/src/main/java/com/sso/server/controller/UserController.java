package com.sso.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.xc.common.component.RestHelper;
import com.xc.common.constant.URLConstant;
import com.xc.common.domain.ResultInfo;
import com.xc.common.domain.UserVO;
import com.xc.common.enums.GlobalStatusEnum;
import com.xc.common.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author ShenHongSheng
 * ClassName: UserController
 * Description:
 * Date: 2020/12/29 16:31
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private RestHelper restHelper;

    @Value("${REDIS_USER_SESSION_KEY}")
    private String REDIS_USER_SESSION_KEY;

    @Value("${SSO_SESSION_EXPIRE}")
    private Integer SSO_SESSION_EXPIRE;


    /**
     * 单点登录接口
     * @author ShenHongSheng
     * version: 2020/12/29
     * @param userName :
     * @param password :
     * @return : ResultInfo<String> 登录校验成功返回token令牌
     */
    @GetMapping(value="/login")
    @ResponseBody
    public ResultInfo<String> userLogin(String userName, String password, HttpServletRequest request, HttpServletResponse response) {
        // 判断账号密码是否正确
        LOGGER.info("RestTemplate调用xc-service-auth服务的getUserInfo接口入参: ", userName);
        ParameterizedTypeReference<ResultInfo<UserVO>> resultType = new ParameterizedTypeReference<ResultInfo<UserVO>>(){};
        ResultInfo<UserVO> resultBody = restHelper.getForEntityFromInner(URLConstant.GET_USER_INFO, HttpMethod.GET, resultType, userName);
        if (!GlobalStatusEnum.SUCCESS.getCode().equals(resultBody.getCode())) {
            LOGGER.error("RestTemplate调用xc-service-auth服务的getUserInfo接口失败！");
            throw new RuntimeException("RestTemplate调用xc-service-auth服务的getUserInfo接口失败！");
        }
        UserVO result = resultBody.getResult();
        if (result == null || password.equals(result.getPassword())) {
            return ResultUtils.error(GlobalStatusEnum.UNAUTHORIZED.getCode(), "账号名或密码错误", "");
        }

        // 生成token
        String token = UUID.randomUUID().toString();
        // 清空密码和盐避免泄漏
        String userPassword = result.getPassword();
        String userSalt = result.getSalt();
        result.setPassword(null);
        result.setSalt(null);
        // 把用户信息写入 redis
//        jedisClient.set(REDIS_USER_SESSION_KEY + ":" + token, JSONObject.toJSON(result));
        // user 已经是持久化对象，被保存在session缓存当中，若user又重新修改属性值，那么在提交事务时，此时 hibernate对象就会拿当前这个user对象和保存在session缓存中的user对象进行比较，如果两个对象相同，则不会发送update语句，否则会发出update语句。
        result.setPassword(userPassword);
        result.setSalt(userSalt);
        // 设置 session 的过期时间
//        jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
        // 添加写 cookie 的逻辑，cookie 的有效期是关闭浏览器就失效。
//        CookieUtils.setCookie(request, response, "USER_TOKEN", token);
        // 返回token
        return ResultUtils.success(token);
    }

    /**
     * 退出登录（删除token令牌）
     * @author ShenHongSheng
     * version: 2020/12/29
     * @param token :
     * @return : String 返回登录页面
     */
    @RequestMapping(value="/logout")
    public String logout(@RequestParam String token) {
//        jedisClient.del(REDIS_USER_SESSION_KEY + ":" + token);
        return "login";
    }

    /**
     * 检验token令牌是否有效
     * @author ShenHongSheng
     * version: 2020/12/29
     * @param token :
     * @return : ResultInfo<UserVO>
     */
    @RequestMapping("/checkToken")
    @ResponseBody
    public ResultInfo<UserVO> queryUserByToken(@RequestParam String token) {
        // 根据token从redis中查询用户信息
        String json = "";
//        String json = jedisClient.get(REDIS_USER_SESSION_KEY + ":" + token);
        // 判断是否为空
        if (StringUtils.isEmpty(json)) {
            return ResultUtils.error(GlobalStatusEnum.EXPIRED_TOKEN.getCode(), "此session已经过期，请重新登录", null);
        }
        // 更新过期时间
//        jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
        // 返回用户信息
        UserVO userVO = JSONObject.parseObject(json, UserVO.class);
        return ResultUtils.success(userVO);
    }
}
