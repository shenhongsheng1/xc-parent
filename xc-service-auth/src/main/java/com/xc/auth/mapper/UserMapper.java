package com.xc.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xc.auth.domain.UserVO;
import com.xc.auth.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ShenHongSheng
 * ClassName: LoginMapper
 * Description:
 * Date: 2020/11/30 15:47
 * @version V1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名称获取用户详细信息
     * @author ShenHongSheng
     * version: 2020/11/30
     * @param userName :
     * @return : User
     */
    UserVO findByUserName(String userName);
}
