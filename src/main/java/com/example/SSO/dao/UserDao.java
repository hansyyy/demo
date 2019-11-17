package com.example.SSO.dao;

import com.example.SSO.domain.entity.User;
import com.example.SSO.domain.entity.userDirection;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author HanSiyue
 * @Date 2019/9/18 下午3:36
 */
@Repository
public interface UserDao {
    /**
     * 登陆
     * @param studentId 学号
     * @param password 密码
     * @return
     */
    User login(@Param("studentId") Integer studentId, @Param("password") String password);

    /**
     * 注册
     * @param userName
     * @param password
     * @param studentId
     * @param mail
     * @param major
     * @return
     */
    Boolean addUser(@Param("userName") String userName, @Param("password") String password ,@Param("studentId") Integer studentId, @Param("mail") String mail, @Param("major") String major);

    /**
     * 添加方向
     * @param list
     * @return
     */
    Boolean insertDirection(@Param("userDirection") List<userDirection> list);

    /**
     * 根据用户名查询用户
     * @param studentId 学号
     * @return
     */
    User selectUserByStudentId(@Param("studentId") Integer studentId);

    /**
     * 修改密码
     * @param studentId 学号
     * @param password 密码
     * @return
     */
    Boolean updatePassword(@Param("studentId") Integer studentId,@Param("password") String password);

    /**
     * 修改个人资料
     * @param studentId
     * @param major
     * @param userName
     * @param headUrl
     * @return
     */
    Boolean updateInfo(@Param("studentId") Integer studentId, @Param("major") String major, @Param("userName") String userName, @Param("headUrl") String headUrl);

    /**
     * 删除方向
     * @param studentId
     * @return
     */
    Boolean deleteDirection(@Param("studentId") Integer studentId);

    /**
     * 展示方向
     * @param studentId
     * @return
     */
    List<Integer> displayDirection(@Param("studentId") Integer studentId);

}
