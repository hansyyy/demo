package com.example.SSO.dao;

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
 * @Date 2019/9/19 下午9:52
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void login() {
        User user = userDao.login(2018210868,"123456");
        Assert.assertNotNull(user);
    }

    @Transactional
    @Test
    public void addUser() {
        Boolean result = userDao.addUser("sl","234567",567890,"67890","hieuhd");
        Assert.assertTrue(result);
    }

    @Test
    public void selectUserByStudentId() {
        User user = userDao.selectUserByStudentId(2018210868);
        Assert.assertNotNull(user);
    }
}