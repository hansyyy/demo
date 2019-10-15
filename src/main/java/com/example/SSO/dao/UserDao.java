package com.example.SSO.dao;

import com.example.SSO.domain.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author HanSiyue
 * @Date 2019/9/18 下午3:36
 */
@Repository
public interface UserDao {
    /**
     * 登陆
     * @param userName 用户名
     * @param password 密码
     * @return
     */
    User login(@Param("userName") String userName, @Param("password") String password);

    /**
     * 注册
     * @param userName 用户名
     * @param password 密码
     * @param studentId 学号
     * @param mail 邮箱
     * @param major 专业
     * @return
     */
    Boolean addUser(@Param("userName") String userName, @Param("password") String password ,@Param("studentId") String studentId, @Param("mail") String mail, @Param("major") String major);

    /**
     * 根据用户名查询用户
     * @param userName 用户名
     * @return
     */
    User selectUserByUserName(@Param("userName") String userName);

}
