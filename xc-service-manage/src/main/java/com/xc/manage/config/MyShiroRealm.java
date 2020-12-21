package com.xc.manage.config;

import com.xc.common.domain.PermissionVO;
import com.xc.common.domain.ResultInfo;
import com.xc.common.domain.RoleVO;
import com.xc.common.domain.UserVO;
import com.xc.common.enums.GlobalStatusEnum;
import com.xc.manage.api.AuthClient;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ShenHongSheng
 * ClassName: MyShiroRealm
 * Description:
 * Date: 2020/11/30 15:06
 * @version V1.0
 */
public class MyShiroRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyShiroRealm.class);

    @Autowired
    private AuthClient authClient;

    /**
     * 认证配置类
     * @author ShenHongSheng
     * version: 2020/11/30
     * @param authenticationToken :
     * @return : AuthenticationInfo
     * throws: AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        LOGGER.info("============认证配置开始===========");
        //principal/credential 配对最常见的例子是用户名和密码。用户名是所声称的身份，密码是匹配所声称的身份的 证明。
        String userName = (String) authenticationToken.getPrincipal();        //Principals(身份)
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("userName", userName);
        UserVO user = null;
        ResultInfo<UserVO> userInfo = authClient.getUserInfo(userName);
        if (userInfo != null && GlobalStatusEnum.SUCCESS.getCode().equals(userInfo.getCode())) {
            user = userInfo.getResult();
        }
        if (userName == null) {
            throw new UnknownAccountException("账号不存在！");
        } else if (user == null) {
            throw new AuthenticationException("用户验证失败！");
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUserName(), //直接传入用户名
                user.getPassword(), //密码
                ByteSource.Util.bytes(user.getCredentialsSalt()),  //密码盐
                getName()  //realm name
        );
        LOGGER.info("============认证配置结束===========");
        return authenticationInfo;
    }

    /**
     * 权限配置类
     * @author ShenHongSheng
     * version: 2020/11/30
     * @param principalCollection : 
     * @return : AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        LOGGER.info("============权限配置开始===========");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //如果身份认证的时候没有传入User对象，这里只能取到userName
        //PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();  //Principals(身份)
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        UserVO user = null;
        ResultInfo<UserVO> userInfo = authClient.getUserInfo(userName);
        if (userInfo != null && GlobalStatusEnum.SUCCESS.getCode().equals(userInfo.getCode())) {
            user = userInfo.getResult();
        }
        if (user == null) {
            throw new UnknownAccountException("账号不存在！");
        }
        for(RoleVO role : user.getRoles()){
            authorizationInfo.addRole(role.getRoleCode());
            for(PermissionVO p : role.getPermissions()){
                authorizationInfo.addStringPermission(p.getPermissionCode());
            }
        }
        LOGGER.info("============权限配置结束===========");
        return authorizationInfo;
    }
}
