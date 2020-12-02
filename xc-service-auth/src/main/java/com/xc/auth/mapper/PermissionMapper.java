package com.xc.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xc.auth.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ShenHongSheng
 * ClassName: PermissionsMapper
 * Description:
 * Date: 2020/11/30 16:06
 * @version V1.0
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
}
