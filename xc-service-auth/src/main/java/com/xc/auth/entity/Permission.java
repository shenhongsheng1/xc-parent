package com.xc.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @author ShenHongSheng
 * ClassName: XcPermission
 * Description:
 * Date: 2020/11/30 15:33
 * @version V1.0
 */
@TableName("xc_auth_permission")
public class Permission {

    private Long id;
    /**
     * 权限代码
     **/
    private String permissionCode;
    /**
     * 权限名称
     **/
    private String permissionName;
    /**
     * 是否可用,如果不可用将不会添加给用户
     **/
    private Boolean available = Boolean.TRUE;
    /**
     * 角色ID
     **/
    private Long roleId;

    private Date createTime;
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
