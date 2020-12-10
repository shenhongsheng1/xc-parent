package com.xc.common.exception;

/**
 * @author ShenHongSheng
 * ClassName: InnerInterfaceException
 * Description:
 * Date: 2020/12/10 17:36
 * @version V1.0
 */
public class InnerInterfaceException extends RuntimeException {

    public InnerInterfaceException(String msg) {
        super(msg);
    }

    public InnerInterfaceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
