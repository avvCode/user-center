package com.vv.usercenter.domain.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterRequest{
    private String userAccount;
    private String userPassword;
    private String checkPassword;
}
