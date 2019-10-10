package com.example.SSO.service.impl;

import com.example.SSO.dao.UserDao;
import com.example.SSO.domain.dto.UserDto;
import com.example.SSO.domain.entity.User;
import com.example.SSO.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @Author HanSiyue
 * @Date 2019/9/18 下午3:39
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public User login(UserDto userDto) {
        if (userDto.getUserName() == null || userDto.getPassword() == null || userDto
                .getVerifyCode() == null){
            return null;
        }else {
            User user = userDao.login(userDto.getUserName(),userDto.getPassword());
            if (user != null) {
                return user;
            }else{
                return null;
            }
        }
    }

    @Override
    public Boolean addUser(String userName, String password, String studentId) {
        if (userDao.selectUserByUserName(userName)!=null){
            return false;
        }else {
            Boolean result = userDao.addUser(userName, password, studentId);
            return result;
        }
    }

    @Override
    public User selectUserByUserName(String userName) {
        User user = userDao.selectUserByUserName(userName);
        if (user!=null){
            return user;
        }else {
            return null;
        }
    }
}
