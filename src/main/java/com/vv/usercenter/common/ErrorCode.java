package com.vv.usercenter.common;

import lombok.Data;

/**
 * 错误码
 */

public enum ErrorCode {
    PARAMS_ERROR(40000,"请求参数错误",""),
    PARAMS_NULL_ERROR(40001,"请求数据为空",""),
    NOT_LOGIN_ERROR(40101,"无请求权限",""),
    NO_AUTHORITY_ERROR(40101,"无请求权限",""),
    ;
    private final int code;
    private final String message;
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }
}
