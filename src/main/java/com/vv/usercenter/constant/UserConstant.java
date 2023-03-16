package com.vv.usercenter.constant;

public interface UserConstant {
    /**
     * 盐加密
     */
    String SALT = "vv";
    /**
     * 用户登录键
     */
    String USER_LOGIN_STATE = "userLoginState";
    /**
     * 普通用户
     */
    Integer DEFAULT_ROLE = 0;
    /**
     * 管理员
     */
    Integer ADMIN_ROLE = 1;
}
