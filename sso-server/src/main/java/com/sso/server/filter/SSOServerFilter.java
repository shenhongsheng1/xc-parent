package com.sso.server.filter;


import com.sso.server.cache.JVMCache;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author ShenHongSheng
 * ClassName: SSOServerFilter
 * Description:
 * Date: 2020/12/22 17:39
 * @version V1.0
 */
public class SSOServerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String service = request.getParameter("service");
        String ticket = request.getParameter("ticket");
        Cookie[] cookies = request.getCookies();
        String username = "";
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if ("sso".equals(cookie.getName())) {
                    username = cookie.getValue();
                    break;
                }
            }
        }
        if (null == service && null != ticket) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (null != username && !"".equals(username)) {
            long time = System.currentTimeMillis();
            String timeString = username + time;
            JVMCache.TICKET_AND_NAME.put(timeString, username);
            StringBuilder url = new StringBuilder();
            url.append(service);
            if (Objects.requireNonNull(service).contains("?")) {
                url.append("&");
            } else {
                url.append("?");
            }
            url.append("ticket=").append(timeString);
            response.sendRedirect(url.toString());
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    /*@Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String token = request.getParameter("token");
        PropertiesTool propertiesTool = PropertiesTool.getInstance();
        String ssoServiceUrl = propertiesTool.getValue("ssoServiceUrl");
        String tokenValidateUrl = propertiesTool.getValue("tokenValidateUrl");
        if (null == token) {
            Cookie cookie = CookieUtil.getCookieByName(request, "token");
            if (null != cookie) {
                token = cookie.getValue();
            }
        }
        if (null == token) {
            //没有token，重定向至sso server登录页
            response.sendRedirect(ssoServiceUrl);
        } else {
            String urlString = request.getRequestURI();
            if (urlString.endsWith("/logout")) {
                String JedisUrl = propertiesTool.getValue("JedisUrl");
                String JedisPort = propertiesTool.getValue("JedisPort");
                RedisProperties.Jedis jedis = new RedisProperties.Jedis(JedisUrl, Integer.parseInt(JedisPort));
                jedis.del(token.getBytes());
                jedis.close();
                SecurityUtils.getSubject().logout();
                response.sendRedirect(ssoServiceUrl);//重定向至sso server登录页
                return;
            }

            //有token,去sso server验证token的有效性
            Map<String, String> map = new HashMap<>();
            map.put("token", token);
            String result = HttpClientUtil.doGet(tokenValidateUrl, map);
            if (StringUtils.isNotBlank(result)) {//验证成功，跳转至首页，并从redis中获取权限
                SystemSession.setUser(SSOTokenUtil.getToken(request));//设置系统session(把用户信息保存在ThreadLocal中)
                Cookie cookie = new Cookie("token", token);
                cookie.setPath("/");
                response.addCookie(cookie);
                filterChain.doFilter(request, response);
            } else {
                response.sendRedirect(ssoServiceUrl);//验证失败，重定向至sso server登录页
            }
        }
    }*/

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

}
