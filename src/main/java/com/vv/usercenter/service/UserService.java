package com.vv.usercenter.service;

import com.vv.usercenter.domain.po.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @author zyz19
* @description 针对表【tb_user(用户)】的数据库操作Service
* @createDate 2023-03-16 15:53:51
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @return 用户id
     */
    long userRegister(String userAccount, String userPassword,String checkPassword);

    /**
     * 用户登录
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param request 请求
     * @return 用户实体
     */
    User login(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户数据脱敏
     * @param originUser
     * @return
     */

    User getSafetyUser(User originUser);
}
