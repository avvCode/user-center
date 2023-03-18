package com.vv.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vv.usercenter.constant.UserConstant;
import com.vv.usercenter.domain.po.User;
import com.vv.usercenter.service.UserService;
import com.vv.usercenter.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author zyz19
* @description 针对表【tb_user(用户)】的数据库操作Service实现
* @createDate 2023-03-16 15:53:51
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        //非空，长度
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            return -1;
        }
        if(userAccount.length() <4){
            return -1;
        }
        if(userPassword.length() < 8 && checkPassword.length() <8){
            return -1;
        }
        //用户名不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if(matcher.find()){
            return -1;
        }
        if(!userPassword.equals(checkPassword)){
            return -1;
        }
        //用户名是否重复
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_account",userAccount);
        int count = this.count(wrapper);
        if(count > 0){
            return -1;
        }
        //密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((UserConstant.SALT+userPassword).getBytes());
        //保存用户
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean result = this.save(user);
        if(!result){
            return -1;
        }
        return user.getId();
    }

    @Override
    public User login(String userAccount, String userPassword, HttpServletRequest request) {

        //非空，长度
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }
        if(userAccount.length() <4){
            return null;
        }

        //用户名不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if(matcher.find()){
            return null;
        }

        //密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((UserConstant.SALT+userPassword).getBytes());
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_account",userAccount).eq("user_password",encryptPassword);
        log.info("account：{},password：{}",userAccount,encryptPassword);
        User user = this.getOne(wrapper);
        if(user == null){
            log.info(" user login failed, userAcount cannot match userPassword");
            return null;
        }
        log.info("user：" + user);
        //用户信息脱敏
        User safetyUser = getSafetyUser(user);
        //记录用户登录状态
        HttpSession session = request.getSession();
        session.setAttribute(UserConstant.USER_LOGIN_STATE,safetyUser);

        return safetyUser;
    }
    @Override
    public User getSafetyUser(User  originUser){
        if(originUser == null){
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setCreateTime(originUser.getCreateTime());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setIsDelete(originUser.getIsDelete());
        safetyUser.setUserRole(originUser.getUserRole());
        return safetyUser;
    }

    @Override
    public int logout(HttpServletRequest request) {
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
        return 0;
    }
}




