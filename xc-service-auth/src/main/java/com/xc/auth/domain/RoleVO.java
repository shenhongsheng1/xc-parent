package com.xc.auth.domain;

import com.xc.auth.entity.Permission;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author ShenHongSheng
 * ClassName: XcRole
 * Description:
 * Date: 2020/11/30 15:32
 * @version V1.0
 */
public class RoleVO {

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
    /**
     * 角色对应权限集合
     */
    private List<PermissionVO> permissions;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
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

    public List<PermissionVO> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionVO> permissions) {
        this.permissions = permissions;
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
