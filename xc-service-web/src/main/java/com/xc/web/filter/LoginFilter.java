package com.xc.web.filter;

import com.xc.common.constant.AuthConst;
import com.xc.web.storage.SessionStorage;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author ShenHongSheng
 * ClassName: LoginFilter
 * Description:
 * Date: 2020/12/22 17:50
 * @version V1.0
 */
public class LoginFilter implements Filter {

    /**
     * sso-client
     * 拦截子系统未登录用户请求，跳转至sso认证中心
     * 接收并存储sso认证中心发送的令牌
     * 与sso-server通信，校验令牌的有效性
     * 建立局部会话
     * 拦截用户注销请求，向sso认证中心发送注销请求
     * 接收sso认证中心发出的注销请求，销毁局部会话
     *
     * sso-server
     * 验证用户的登录信息
     * 创建全局会话
     * 创建授权令牌
     * 与sso-client通信发送令牌
     * 校验sso-client令牌有效性
     * 系统注册
     * 接收sso-client注销请求，注销所有会话
     * */
    private FilterConfig config;

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();

        // 已经登录，放行
        if (session.getAttribute(AuthConst.IS_LOGIN) != null) {
            chain.doFilter(req, res);
            return;
        }
        // 从认证中心回跳的带有token的请求，有效则放行
        String token = request.getParameter(AuthConst.TOKEN);
        if (token != null) {
            session.setAttribute(AuthConst.IS_LOGIN, true);
            session.setAttribute(AuthConst.TOKEN, token);
            // 存储，用于注销
            SessionStorage.INSTANCE.set(token, session);
            chain.doFilter(req, res);
            return;
        }

        // 重定向至登录页面，并附带当前请求地址
        response.sendRedirect(config.getInitParameter(AuthConst.LOGIN_URL) + "?" + AuthConst.CLIENT_URL + "=" + request.getRequestURL());
    }

    @Override
    public void init(FilterConfig filterConfig) {
        config = filterConfig;
    }
}
