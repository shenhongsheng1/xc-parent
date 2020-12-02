package com.xc.common.enums;

/**
 * @author ShenHongSheng
 * ClassName: GlobalStatusEnum
 * Description:
 * Date: 2020/11/26 15:29
 * @version V1.0
 */
public enum GlobalStatusEnum {

    SUCCESS("0000", "服务调用成功"),
    INTERNAL_ERROR("0001", "服务内部异常"),
    NOT_ACCEPTABLE("0002", "请求参数错误"),
    UNAUTHORIZED("0003", "未授权用户"),
    EXPIRED_TOKEN("0004", "Token过期"),
    BAD_REQUEST("0005", "请求存在错误");

    private final String code;
    private final String message;

    GlobalStatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String toString() {
        return this.code;
    }

    /*public static GlobalStatusEnum typeOf(String code) {
        GlobalStatusEnum status = resolve(code);
        if (status == null) {
            throw new IllegalArgumentException("No matching constant for [" + code + "]");
        } else {
            return status;
        }
    }

    @Nullable
    public static GlobalStatusEnum resolve(String code) {
        GlobalStatusEnum[] var1 = values();
        int var2 = var1.length;
        for(int var3 = 0; var3 < var2; ++var3) {
            GlobalStatusEnum status = var1[var3];
            if (status.code.equals(code)) {
                return status;
            }
        }
        return null;
    }*/
}
