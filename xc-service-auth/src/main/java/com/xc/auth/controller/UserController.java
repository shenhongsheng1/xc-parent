package com.xc.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xc.auth.entity.User;
import com.xc.auth.service.UserService;
import com.xc.common.domain.ResultInfo;
import com.xc.common.domain.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping(value = "/auth/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 根据用户名获取用户信息
     * @author ShenHongSheng
     * version: 2020/12/7
     * @param userName : 用户名
     * @return : ResultInfo<UserVO>
     */
    @GetMapping(value = "/getUserInfo")
    @ResponseBody
    public ResultInfo<UserVO> getUserInfo(@RequestParam String userName) {
        LOGGER.info("UserController: {}", userName);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userName);
        User user = userService.getOne(wrapper);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return new ResultInfo<>(userVO);
    }

    @PostMapping(value = "/addUser")
    @ResponseBody
    public ResultInfo<String> addUser(@RequestBody UserVO userVO) {
        LOGGER.info("userVO: {}", JSONObject.toJSON(userVO));
        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        boolean save = userService.save(user);
        String result = "添加成功！";
        if (!save) {
            result = "添加失败！";
        }
        return new ResultInfo<>(result);
    }
}
