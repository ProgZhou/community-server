package com.progzhou.community.common;

public enum ApiResponseCodeEnum {

    SUCCESS(0, "请求成功"),

    UNKNOW_FAIL(99999, "未知的异常");

    private Integer code;
    private String message;

    ApiResponseCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public Integer getCode() {
        return this.code;
    }
}
