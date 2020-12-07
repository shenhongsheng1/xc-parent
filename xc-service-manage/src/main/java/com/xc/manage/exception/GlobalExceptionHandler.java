package com.xc.manage.exception;

import com.xc.common.domain.ResultInfo;
import com.xc.common.enums.GlobalStatusEnum;
import com.xc.common.utils.ResultUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ShenHongSheng
 * ClassName: GloablExceptionHandler
 * Description: 自定义全局异常处理
 * Date: 2020/11/25 11:05
 * @version V1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 声明要捕获的异常(自定义异常和Exception)
     * @author ShenHongSheng
     * version: 2020/12/1
     * @param en :
     * @return : ResultInfo<?>
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultInfo<?> exceptionHandler(Exception en) {
        en.printStackTrace();
        if (en instanceof AuthenticationException) {
            LOGGER.error("权限异常：" + en.getMessage());
            return ResultUtils.error(GlobalStatusEnum.UNAUTHORIZED.getCode(), "权限异常：" + en.getMessage());
        } else if(en instanceof RuntimeException) {
            LOGGER.error("业务异常：" + en.getMessage());
            return ResultUtils.error(GlobalStatusEnum.INTERNAL_ERROR.getCode(), "业务异常：" + en.getMessage());
        } else {
            LOGGER.error("系统异常：" + en.getMessage());
            return ResultUtils.error(GlobalStatusEnum.INTERNAL_ERROR.getCode(), "系统异常：" + en.getMessage());
        }
    }
}
