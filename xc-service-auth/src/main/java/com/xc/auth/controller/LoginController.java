package com.xc.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xc.auth.entity.User;
import com.xc.auth.service.UserService;
import com.xc.common.domain.ResultInfo;
import com.xc.common.domain.UserVO;
import com.xc.common.enums.GlobalStatusEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ShenHongSheng
 * ClassName: LoginController
 * Description:
 * Date: 2020/11/30 15:46
 * @version V1.0
 */
@RestController
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    @ResponseBody
    public ResultInfo<UserVO> login(String userName, String password) {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            throw new RuntimeException(GlobalStatusEnum.NOT_ACCEPTABLE.getMessage());
        }

        //将用户名和密码封装到UsernamePasswordToken
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
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
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userName);
        User user = userService.getOne(wrapper);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return new ResultInfo<>(userVO);
    }
}
