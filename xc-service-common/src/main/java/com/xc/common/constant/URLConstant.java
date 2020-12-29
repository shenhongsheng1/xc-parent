package com.xc.common.constant;

/**
 * @author ShenHongSheng
 * ClassName: URLConstant
 * Description:
 * Date: 2020/12/29 17:28
 * @version V1.0
 */
public class URLConstant {

    public static final String SSO_SERVER = "http://sso-server/";
    public static final String XC_SERVICE_AUTH = "http://xc-service-auth/";
    public static final String XC_SERVICE_HOTEL = "http://xc-service-hotel/";
    public static final String XC_SERVICE_MANAGE = "http://xc-service-manage/";
    public static final String XC_SERVICE_WEB = "http://xc-service-web/";



    public static final String GET_USER_INFO = XC_SERVICE_AUTH + "auth/user/getUserInfo?userName={1}";
}
