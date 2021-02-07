package com.xc.web.interceptors;

import com.xc.common.component.RestHelper;
import com.xc.common.domain.ResultInfo;
import com.xc.common.domain.UserVO;
import com.xc.common.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ShenHongSheng
 * ClassName: UserLoginHandlerInterceptor
 * Description:
 * Date: 2021/2/5 15:06
 * @version V1.0
 */
@Component
public class UserLoginHandlerInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginHandlerInterceptor.class);

    private static final String COOKIE_NAME = "USER_TOKEN";

    @Autowired
    private RestHelper restHelper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 主动注销，即子系统提供的注销请求
        if ("/logout".equals(request.getRequestURI())) {
            // 向认证中心发送注销请求
            response.sendRedirect("http://localhost:8005/user/logout");
            return false;
        }

        String token = CookieUtils.getCookieValue(request, COOKIE_NAME);
        UserVO userVO = null;
        if (StringUtils.isNotEmpty(token)) {
            ParameterizedTypeReference<ResultInfo<UserVO>> resultType = new ParameterizedTypeReference<ResultInfo<UserVO>>(){};
            String url = "http://sso-server/user/checkToken?token=" + token;
            ResultInfo<UserVO> resultBody = restHelper.getForEntityFromInner(url, HttpMethod.GET, resultType);
            if (resultBody == null) {
                LOGGER.error("RestTemplate调用sso-server服务的checkToken接口失败！");
                throw new RuntimeException("RestTemplate调用sso-server服务的checkToken接口失败！");
            }
            userVO = resultBody.getResult();
        }
        if (StringUtils.isEmpty(token) || null == userVO) {
            // 跳转到登录页面，把用户请求的url作为参数传递给登录页面。
            response.sendRedirect("http://localhost:8005/login?redirect=" + request.getRequestURL());
            // 返回false
            return false;
        }
        // 把用户信息放入Request
        request.setAttribute("user", userVO);
        // 返回值决定handler是否执行。true：执行，false：不执行。
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)  { }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)  { }
}
