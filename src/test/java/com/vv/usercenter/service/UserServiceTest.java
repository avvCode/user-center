package com.vv.usercenter.service;
import java.util.Date;

import com.vv.usercenter.domain.po.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Resource
   private UserService userService;

    @Test
    void testInsert(){

        User user = new User();
        user.setUserAccount("1111");
        user.setUsername("1111");
        user.setAvatarUrl("1111");
        user.setUserPassword("1111");
        user.setPhone("1111");
        user.setEmail("1111");
        userService.save(user);
        System.out.println(user.getId());
    }
    @Test
    void testSelect(){
        System.out.println(userService.list(null));
    }

    @Test
    void userRegister() {
        String userAccount = "vvvv";
        String userPassword = "123456";
        String checkPassword = "123456";
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);
        userAccount = "vv";
        userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);
        userAccount = "vvvv";
        userPassword = "12345678";
        userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);
        userAccount = "vv vv";
        userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);
        userAccount = "vvvv";
        userPassword = "12345678";
        checkPassword = "12345678";
        userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);
    }


}