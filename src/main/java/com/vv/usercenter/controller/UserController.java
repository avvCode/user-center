package com.vv.usercenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vv.usercenter.common.BaseResponse;
import com.vv.usercenter.common.ErrorCode;
import com.vv.usercenter.common.ResultUtils;
import com.vv.usercenter.constant.UserConstant;
import com.vv.usercenter.domain.po.User;
import com.vv.usercenter.domain.request.UserLoginRequest;
import com.vv.usercenter.domain.request.UserRegisterRequest;
import com.vv.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> register(@RequestBody  UserRegisterRequest userRegisterRequest){
        if(userRegisterRequest == null){
            return null;
        }

        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
           return null;
        }

        long l = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(l);
    }

    @PostMapping("/login")
    public BaseResponse<User> login(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if(userLoginRequest == null){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }
        User user = userService.login(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> delete(@RequestBody Map<String,Object> param,HttpServletRequest request){
        Integer id = (Integer) param.get("id");
        if(!isAdmin(request)){
            return ResultUtils.error(ErrorCode.NO_AUTHORITY_ERROR);
        }
        if(id == null || id <= 0){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(userService.removeById(id));
    }
    @PostMapping("/search")
    public BaseResponse<List<User>> searchUsers(@RequestBody Map<String,Object> param,HttpServletRequest request){
       if (!isAdmin(request)){
           return null;
       }
        String username = (String) param.get("username");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username",username);
        List<User> userList = userService.list(queryWrapper);
        List<User> collect = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(collect);
    }


    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if(user == null){
            return null;
        }
        User safetyUser = userService.getSafetyUser(userService.getById(user.getId()));
        return ResultUtils.success(safetyUser);
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> logout(HttpServletRequest request){
        if (request == null){
            return null;
        }
        return ResultUtils.success(userService.logout(request));
    }

    private boolean isAdmin(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if(user == null || !user.getUserRole().equals(UserConstant.ADMIN_ROLE)){
            return false;
        }
        return true;
    }
}
