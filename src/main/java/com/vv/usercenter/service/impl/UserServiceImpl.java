package com.vv.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vv.usercenter.domain.po.User;
import com.vv.usercenter.service.UserService;
import com.vv.usercenter.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author zyz19
* @description 针对表【tb_user(用户)】的数据库操作Service实现
* @createDate 2023-03-16 15:53:51
*/
@Service
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
        final String SALT = "vv";
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());
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
}




