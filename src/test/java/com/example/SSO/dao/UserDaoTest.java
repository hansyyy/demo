package com.example.SSO.dao;

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
 * @Date 2019/9/19 下午9:52
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void login() {
        User user = userDao.login("hsy","123456");
        Assert.assertNotNull(user);
    }

    @Transactional
    @Test
    public void addUser() {
        Boolean result = userDao.addUser("sl","234567","567890");
        Assert.assertTrue(result);
    }

    @Test
    public void selectUserByUserName() {
        User user = userDao.selectUserByUserName("hsy");
        Assert.assertNotNull(user);
    }
}