package com.xc.web.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
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

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        if ((boolean) session.getAttribute("isLogin")) {
            chain.doFilter(request, response);
            return;
        }
        //跳转至sso认证中心
        res.sendRedirect("sso-server-url-with-system-url");
    }
}
