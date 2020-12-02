package com.xc.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xc.auth.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ShenHongSheng
 * ClassName: RoleMapper
 * Description:
 * Date: 2020/11/30 16:05
 * @version V1.0
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}
