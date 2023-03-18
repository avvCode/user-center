package com.vv.usercenter.exception;


import com.vv.usercenter.common.BaseResponse;
import com.vv.usercenter.common.ErrorCode;
import com.vv.usercenter.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    /**
     * 出现业务异常，直接捕获
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e){
        log.error("BusinessException：",e.getMessage(),e);
        return ResultUtils.error(e.getCode(),e.getMessage(),"");
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException e){
        log.error("RuntimeException：",e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,e.getMessage(),"");
    }
}
