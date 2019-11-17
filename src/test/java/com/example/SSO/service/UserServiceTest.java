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

import java.util.ArrayList;
import java.util.List;

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


    /*
    @Test
    public void login() {
        UserDto userDto = new UserDto();
        userDto.setStudentId(2018210868);
        userDto.setPassword("123456");
        userDto.setVerifyCode("fghj");
        User user = userService.login(userDto);
        Assert.assertNotNull(user);

    }
     */


    @Transactional
    @Test
    public void addUser() {
        List<Integer> list = new ArrayList();
        list.add(1);
        list.add(2);
        Boolean result = userService.addUser("sl","234567",4567890,"67890","hieuhd",list);
        Assert.assertTrue(result);
    }

    @Test
    public void selectUserByStudentId() {
        User user = userService.selectUserByStudentId(2018210868);
        Assert.assertNotNull(user);
    }

    /*
    @Test
    public void sendMail(){
        Boolean result = userService.sendMail("975444913@qq.com","hahahahah","韩思玥发个你的邮件");
        Assert.assertTrue(result);
    }
     */

}