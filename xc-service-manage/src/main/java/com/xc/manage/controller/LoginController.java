package com.xc.manage.controller;

import com.xc.common.enums.GlobalStatusEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ShenHongSheng
 * ClassName: LoginController
 * Description:
 * Date: 2020/11/30 15:46
 * @version V1.0
 */
@Controller
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @GetMapping(value = "/login")
    public String Login() {
        return "/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new RuntimeException(GlobalStatusEnum.NOT_ACCEPTABLE.getMessage());
        }
        //将用户名和密码封装到UsernamePasswordToken
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //登录认证
        try {
            SecurityUtils.getSubject().login(token);// 传到MyAuthorizingRealm类中的方法进行认证
        }catch (UnknownAccountException e) {
            LOGGER.error("登录失败：{}", e.getMessage());
            throw new UnknownAccountException(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            LOGGER.error("登录失败：{}", e.getMessage());
            throw new IncorrectCredentialsException(e.getMessage());
        } catch (AuthenticationException e) {
            LOGGER.error("登录失败：{}", e.getMessage());
            throw new AuthenticationException(e.getMessage());
        }
        return "/index";
    }
}
