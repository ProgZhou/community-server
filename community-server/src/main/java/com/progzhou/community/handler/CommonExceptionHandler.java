package com.progzhou.community.handler;

import com.progzhou.community.common.BusinessException;
import com.progzhou.community.common.response.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public CommonResult<String> handleBusinessException(BusinessException e) {
        log.error("exception: {}, message: {}", e, e.getMessage());
        return CommonResult.fail(e.getTraceId(), e.getCode(), e.getMessage());
    }

}
