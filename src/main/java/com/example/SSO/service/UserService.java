package com.example.SSO.service;

import com.example.SSO.domain.dto.UserDto;
import com.example.SSO.domain.entity.User;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

/**
 * @Author HanSiyue
 * @Date 2019/9/18 下午3:37
 */
@Service
public interface UserService {
    /**
     * 登陆
     * @param userDto userDto
     * @return
     */
    User login(UserDto userDto);

    /**
     * 注册
     * @param userName 用户名
     * @param password 密码
     * @return
     */
    Boolean addUser(@Param("userName") String userName, @Param("password") String password, @Param("studentId") String studentId);

    /**
     * 根据用户名查询用户
     * @param userName 用户名
     * @return
     */
    User selectUserByUserName(@Param("userName") String userName);
}