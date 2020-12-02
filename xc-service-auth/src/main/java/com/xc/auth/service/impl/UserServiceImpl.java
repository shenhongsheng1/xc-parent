package com.xc.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xc.auth.entity.User;
import com.xc.auth.mapper.UserMapper;
import com.xc.auth.service.UserService;
import com.xc.common.domain.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ShenHongSheng
 * ClassName: LoginServiceImpl
 * Description:
 * Date: 2020/11/30 15:46
 * @version V1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public UserVO findByUserName(String username) {
        return userMapper.findByUserName(username);
    }
}
