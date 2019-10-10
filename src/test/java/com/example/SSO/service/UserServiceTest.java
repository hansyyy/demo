package com.example.SSO.service;

import com.example.SSO.domain.dto.UserDto;
import com.example.SSO.domain.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * @Author HanSiyue
 * @Date 2019/9/19 下午10:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void login() {
        UserDto userDto = new UserDto();
        userDto.setUserName("hsy");
        userDto.setPassword("123456");
        userDto.setVerifyCode("fghj");
        User user = userService.login(userDto);
        Assert.assertNotNull(user);

    }

    @Transactional
    @Test
    public void addUser() {
        Boolean result = userService.addUser("sl","234567","4567890");
        Assert.assertTrue(result);
    }

    @Test
    public void selectUserByUserName() {
        User user = userService.selectUserByUserName("hsy");
        Assert.assertNotNull(user);
    }
}