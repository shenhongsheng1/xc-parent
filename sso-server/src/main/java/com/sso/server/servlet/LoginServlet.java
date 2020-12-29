package com.sso.server.servlet;

import com.sso.server.cache.JVMCache;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ShenHongSheng
 * ClassName: LoginServlet
 * Description:
 * Date: 2020/12/22 17:46
 * @version V1.0
 */
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = -3170191388656385924L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String service = request.getParameter("service");
        if ("cloud".equals(username) && "cloud".equals(password)) {
            Cookie cookie = new Cookie("sso", username);
            cookie.setPath("/");
            response.addCookie(cookie);
            long time = System.currentTimeMillis();
            String timeString = username + time;
            JVMCache.TICKET_AND_NAME.put(timeString, username);
            if (null != service) {
                StringBuilder url = new StringBuilder();
                url.append(service);
                if (service.contains("?")) {
                    url.append("&");
                } else {
                    url.append("?");
                }
                url.append("ticket=").append(timeString);
                response.sendRedirect(url.toString());
            } else {
                response.sendRedirect("/sso/index.jsp");
            }
        } else {
            response.sendRedirect("/sso/index.jsp?service=" + service);
        }
    }
}
