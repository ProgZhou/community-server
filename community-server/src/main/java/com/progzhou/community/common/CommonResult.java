package com.progzhou.community.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CommonResult<T> implements Serializable {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 链路id 用于查询日志
     */
    private String traceId;

    /**
     * 响应时间戳
     */
    private Long timestamp;

    /**
     * 响应数据
     */
    private T data;

    public CommonResult(String traceId, Integer code) {
        this.traceId = traceId;
        this.timestamp = System.currentTimeMillis();
        this.code = code;
    }

    public CommonResult(String traceId, Integer code, String message) {
        this.traceId = traceId;
        this.timestamp = System.currentTimeMillis();
        this.code = code;
        this.message = message;
    }

    public CommonResult(String traceId, Integer code, String message, T data) {
        this.traceId = traceId;
        this.timestamp = System.currentTimeMillis();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public CommonResult(String traceId, ApiResponseCodeEnum apiResponseCodeEnum) {
        this.traceId = traceId;
        this.timestamp = System.currentTimeMillis();
        this.code = apiResponseCodeEnum.getCode();
        this.message = apiResponseCodeEnum.getMessage();
    }

    public static <T> CommonResult<T> success(String traceId) {
        return new CommonResult<>(traceId, ApiResponseCodeEnum.SUCCESS.getCode(), ApiResponseCodeEnum.SUCCESS.getMessage());
    }

    public static <T> CommonResult<T> success(String traceId, T data) {
        return new CommonResult<>(traceId, ApiResponseCodeEnum.SUCCESS.getCode(), ApiResponseCodeEnum.SUCCESS.getMessage(), data);
    }

}
