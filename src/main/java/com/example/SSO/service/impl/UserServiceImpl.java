package com.example.SSO.service.impl;

import com.example.SSO.dao.UserDao;
import com.example.SSO.domain.dto.UserDto;
import com.example.SSO.domain.entity.User;
import com.example.SSO.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @Author HanSiyue
 * @Date 2019/9/18 下午3:39
 */
@Service
public class UserServiceImpl implements UserService {

    @Value("${spring.mail.username}")
    private String from;
    @Resource
    private UserDao userDao;
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public User login(UserDto userDto) {
        if (userDto.getStudentId() == null || userDto.getPassword() == null || userDto
                .getVerifyCode() == null){
            return null;
        }else {
            User user = userDao.login(userDto.getStudentId(),userDto.getPassword());
            if (user != null) {
                return user;
            }else{
                return null;
            }
        }
    }

    @Override
    public Boolean addUser(String userName, String password, Integer studentId, String mail, String major) {
        if (userDao.selectUserByStudentId(studentId)!=null){
            return false;
        }else {
            Boolean result = userDao.addUser(userName, password, studentId,mail,major);
            return result;
        }
    }

    @Override
    public User selectUserByStudentId(Integer studentId) {
        User user = userDao.selectUserByStudentId(studentId);
        if (user!=null){
            return user;
        }else {
            return null;
        }
    }

    @Override
    public Boolean sendMail(String to, String subject, String context) {
        try{
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(context);
            simpleMailMessage.setFrom(from);
            javaMailSender.send(simpleMailMessage);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

}
