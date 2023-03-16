package com.vv.usercenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vv.usercenter.constant.UserConstant;
import com.vv.usercenter.domain.po.User;
import com.vv.usercenter.domain.request.UserLoginRequest;
import com.vv.usercenter.domain.request.UserRegisterRequest;
import com.vv.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Long register(@RequestBody  UserRegisterRequest userRegisterRequest){
        if(userRegisterRequest == null){
            return null;
        }

        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
           return null;
        }

        return userService.userRegister(userAccount, userPassword, checkPassword);
    }

    @PostMapping("/login")
    public User login(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if(userLoginRequest == null){
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }
        User user = userService.login(userAccount, userPassword, request);
        return user;
    }

    @PostMapping("/delete")
    public boolean delete(@RequestBody Map<String,Object> param,HttpServletRequest request){
        Integer id = (Integer) param.get("id");
        if(!isAdmin(request)){
            return false;
        }
        if(id == null || id <= 0){
            return false;
        }
        return userService.removeById(id);
    }
    @PostMapping("/search")
    public List<User> searchUsers(@RequestBody Map<String,Object> param,HttpServletRequest request){
       if (!isAdmin(request)){
           return new ArrayList<>();
       }
        String username = (String) param.get("username");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username",username);
        List<User> userList = userService.list(queryWrapper);
        return userList.stream().map(user -> {
            user.setUserPassword(null);
            return user;
        }).collect(Collectors.toList());
    }
    private boolean isAdmin(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if(user == null || !user.getUserRole().equals(UserConstant.ADMIN_ROLE)){
            return false;
        }
        return true;
    }
}
