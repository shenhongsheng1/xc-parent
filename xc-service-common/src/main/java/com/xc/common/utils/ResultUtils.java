package com.xc.common.utils;

import com.xc.common.domain.ResultInfo;

import java.util.HashMap;

/**
 * @author ShenHongSheng
 * ClassName: ResultUtil
 * Description:
 * Date: 2020/11/26 16:51
 * @version V1.0
 */
public class ResultUtils {

    // 统一结果集对象 使用ResultBody
    private static ResultInfo result = new ResultInfo();

    /**
     * 成功时调用的函数
     * @param result    要返回的对象
     * @return ResultInfo<T>
     */
    public static <T> ResultInfo<T> success(T result) {
        ResultInfo<T> resultInfo = new ResultInfo<>(result);
        resultInfo.setDetailedMessage(new HashMap<>());
        return resultInfo;
    }

    /**
     * 失败时调用的函数
     * @param code    要返回的状态码
     * @param message 要返回的信息
     * @return ResultInfo
     */
    public static ResultInfo error(String code , String message) {
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 失败时调用的函数
     * @param code    要返回的状态码
     * @param message 要返回的信息
     * @param result  要返回的对象
     * @return ResultInfo<T>
     */
    public static <T> ResultInfo<T> error(String code , String message, T result) {
        ResultInfo<T> resultInfo = new ResultInfo<>(result);
        resultInfo.setCode(code);
        resultInfo.setMessage(message);
        resultInfo.setDetailedMessage(new HashMap<>());
        return resultInfo;
    }
}
