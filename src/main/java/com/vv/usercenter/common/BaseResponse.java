package com.vv.usercenter.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;


/**
 * 通用返回类
 * @param <T>
 */
@Data
@AllArgsConstructor
public class BaseResponse<T> implements Serializable {
    private int code;
    private T data;
    private String message;

    public BaseResponse(int code, T data) {
        this.code = code;
        this.data = data;
    }
}