package com.company.project.config.exception;

import com.company.project.config.result.ResultMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * GlobalExceptionHandler
 * </p>
 *
 * @author 程序员蜗牛
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理请求时发生的异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public Object exceptionHandler(HttpServletRequest request, Exception e){
        LOGGER.error("未知异常，请求地址：{}, 错误信息：{}", request.getRequestURI(), e.getMessage());
        if(e instanceof CommonException){
            CommonException ex = (CommonException)e;
            return ResultMsg.fail(ex.getCode(), e.getMessage());
        }
        return ResultMsg.fail(999, e.getMessage());
    }
}
