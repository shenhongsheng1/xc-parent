package com.xc.common.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author ShenHongSheng
 * ClassName: XcUser
 * Description:
 * Date: 2020/11/30 15:31
 * @version V1.0
 */
public class UserVO {

    private Long id;
    /**
     * 登录用户名
     */
    private String userName;
    /**
     * 名称（昵称或者真实姓名，根据实际情况定义）
     */
    private String name;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 用户邮件
     */
    private String email;
    /**
     * 加密密码的盐
     */
    private String salt;
    /**
     * 用户状态：0：创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1：正常状态，2：用户被锁定。
     */
    private byte status;
    /**
     * 过期日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date expiredDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime;

    /**
     * 用户对应的角色集合
     */
    private List<RoleVO> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
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

    public List<RoleVO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleVO> roles) {
        this.roles = roles;
    }

    /**
     * 密码盐. 重新对盐重新进行了定义，用户名+salt，这样就不容易被破解，可以采用多种方式定义加盐
     * @return
     */
    public String getCredentialsSalt(){
        return this.userName + this.salt;
    }
}
