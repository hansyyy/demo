package com.example.SSO.service;

import com.example.SSO.domain.dto.UserDto;
import com.example.SSO.domain.entity.User;
import com.example.SSO.domain.po.UserPo;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    User login(HttpServletRequest request, UserDto userDto);

    User login2(HttpServletRequest request, @Param("password") String password ,@Param("studentId") Integer studentId);

    /**
     * 注册
     * @param userName
     * @param password
     * @param studentId
     * @param mail
     * @param major
     * @return
     */
    Boolean addUser(@Param("userName") String userName, @Param("password") String password ,@Param("studentId") Integer studentId, @Param("mail") String mail, @Param("major") String major, @Param("directions") List<Integer> directions);

    /**
     * 根据用户名查询用户
     * @param studentId 学号
     * @return
     */
    User selectUserByStudentId(@Param("studentId") Integer studentId);


    /**
     * 发送邮件
     * @param request
     * @return
     */
    Boolean sendMail(HttpServletRequest request);

    /**
     * 生成验证码
     * @param request
     * @param response
     * @return
     */
    Object verifycode(HttpServletRequest request, HttpServletResponse response);

    /**
     * 修改密码
     * @param request
     * @param password 密码
     * @return
     */
    Boolean updatePassword(HttpServletRequest request,@Param("password") String password,String mailVerifyCode);

    /**
     * 修改个人资料
     * @param request
     * @param major
     * @param userName
     * @param headUrl
     * @param directions
     * @return
     */
    Boolean updateInfo(HttpServletRequest request,@Param("major") String major,@Param("userName") String userName,@Param("head") String headUrl, @Param("directions") List<Integer> directions);

    /**
     * 展示个人信息
     * @param request
     * @return
     */
    UserPo displayInfo(HttpServletRequest request);
}