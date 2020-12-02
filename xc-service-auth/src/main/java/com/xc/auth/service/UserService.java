package com.xc.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xc.auth.domain.UserVO;
import com.xc.auth.entity.User;

/**
 * @author ShenHongSheng
 * ClassName: LoginService
 * Description:
 * Date: 2020/11/30 15:46
 * @version V1.0
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户名称获取用户详细信息
     * @author ShenHongSheng
     * version: 2020/11/30
     * @param username :
     * @return : User
     */
    UserVO findByUserName(String username);
}
