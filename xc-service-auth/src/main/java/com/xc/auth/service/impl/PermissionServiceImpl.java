package com.xc.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xc.auth.entity.Permission;
import com.xc.auth.mapper.PermissionMapper;
import com.xc.auth.service.PermissionService;
import org.springframework.stereotype.Service;

/**
 * @author ShenHongSheng
 * ClassName: PermissionsServiceImpl
 * Description:
 * Date: 2020/11/30 16:07
 * @version V1.0
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
}
