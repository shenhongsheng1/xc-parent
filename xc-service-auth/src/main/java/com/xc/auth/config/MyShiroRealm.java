package com.xc.auth.config;

import com.xc.auth.domain.PermissionVO;
import com.xc.auth.domain.RoleVO;
import com.xc.auth.domain.UserVO;
import com.xc.auth.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
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
    private UserService userService;

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
        UserVO user = userService.findByUserName(userName);
        for(RoleVO role : user.getRoles()){
            authorizationInfo.addRole(role.getRoleCode());
            for(PermissionVO p : role.getPermissions()){
                authorizationInfo.addStringPermission(p.getPermissionCode());
            }
        }
        LOGGER.info("============权限配置结束===========");
        return authorizationInfo;
    }

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
        char[] credentials =  (char[]) authenticationToken.getCredentials();    //Credentials(凭证)
        String password = new String(credentials);
        UserVO user = userService.findByUserName(userName);
        if (userName == null) {
            throw new UnknownAccountException("账号不存在！");
        } else if (user == null) {
            throw new AuthenticationException("用户验证失败！");
        } else {
            if (!password.equals(user.getPassword())) {
                //数据库密码经过MD5加密，这个需要解密后比较
//                throw new IncorrectCredentialsException("密码错误！");
            }
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user, //这里传入的是user对象，比对的是用户名，直接传入用户名也没错，但是在授权部分就需要自己重新从数据库里取权限
                user.getPassword(), //密码
                getName()  //realm name
        );
        LOGGER.info("============认证配置结束===========");
        return authenticationInfo;
    }
}
