package com.xc.common.domain;

import com.xc.common.enums.GlobalStatusEnum;
import java.util.Map;

/**
 * @author ShenHongSheng
 * ClassName: ResultResponse
 * Description:
 * Date: 2020/11/26 14:29
 * @version V1.0
 */
public class ResultInfo<T> {

    private String code;
    private String message;
    private Map<String, Object> detailedMessage;
    private T result;

    public ResultInfo() {
        this(GlobalStatusEnum.SUCCESS.getCode(), GlobalStatusEnum.SUCCESS.getMessage(), null, null);
    }

    public ResultInfo(T result) {
        this(GlobalStatusEnum.SUCCESS.getCode(), GlobalStatusEnum.SUCCESS.getMessage(), null, result);
    }

    public ResultInfo(String code, String message, Map<String, Object> detailedMessage) {
        this(code, message, detailedMessage, null);
    }

    public ResultInfo(String code, String message, Map<String, Object> detailedMessage, T result) {
        this.code = code;
        this.message = message;
        this.detailedMessage = detailedMessage;
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getDetailedMessage() {
        return detailedMessage;
    }

    public void setDetailedMessage(Map<String, Object> detailedMessage) {
        this.detailedMessage = detailedMessage;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
