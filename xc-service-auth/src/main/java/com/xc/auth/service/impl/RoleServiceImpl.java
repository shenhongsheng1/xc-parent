package com.xc.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xc.auth.entity.Role;
import com.xc.auth.mapper.RoleMapper;
import com.xc.auth.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * @author ShenHongSheng
 * ClassName: RoleServiceImpl
 * Description:
 * Date: 2020/11/30 16:04
 * @version V1.0
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
}
