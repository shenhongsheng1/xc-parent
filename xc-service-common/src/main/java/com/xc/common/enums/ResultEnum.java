package com.xc.common.enums;

/**
 * @author ShenHongSheng
 * ClassName: ResultEnum
 * Description:
 * Date: 2020/11/25 17:53
 * @version V1.0
 */
public enum ResultEnum {

    CODE_200("200", ""),
    CODE_400("400", "错误的请求参数"),
    CODE_401("401", "没有登录"),
    CODE_402("402", "用户名或密码错误"),
    CODE_403("403", "没有权限"),
    CODE_404("404", "用户不存在"),
    CODE_405("405", "用户被冻结"),
    CODE_406("406", "信息重复"),
    CODE_500("500", "内部服务器错误");

    private final String code;
    private final String message;

    ResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
