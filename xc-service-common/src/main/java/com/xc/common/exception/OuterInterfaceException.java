package com.xc.common.exception;

/**
 * @author ShenHongSheng
 * ClassName: OuterInterfaceException
 * Description:
 * Date: 2020/12/10 17:37
 * @version V1.0
 */
public class OuterInterfaceException extends RuntimeException {

    public OuterInterfaceException(String msg) {
        super(msg);
    }

    public OuterInterfaceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
