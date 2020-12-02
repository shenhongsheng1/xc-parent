package com.xc.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @author ShenHongSheng
 * ClassName: XcRole
 * Description:
 * Date: 2020/11/30 15:32
 * @version V1.0
 */
@TableName("xc_auth_role")
public class Role {

    private Long id;
    /**
     * 角色代码
     **/
    private String roleCode;
    /**
     * 角色名称
     **/
    private String roleName;
    /**
     * 角色描述
     **/
    private String description;
    /**
     * 是否可用,如果不可用将不会添加给用户
     **/
    private Boolean available = Boolean.TRUE;
    /**
     * 用户ID
     **/
    private Long userId;

    private Date createTime;
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
