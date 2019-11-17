package com.example.SSO.service.impl;

import com.example.SSO.constant.ConstantKit;
import com.example.SSO.dao.UserDao;
import com.example.SSO.domain.dto.UserDto;
import com.example.SSO.domain.entity.User;
import com.example.SSO.domain.entity.userDirection;
import com.example.SSO.domain.po.UserPo;
import com.example.SSO.service.UserService;
import com.example.SSO.util.TokenUtil;
import com.example.SSO.util.VerifyUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    /*
    @Autowired
    Configuration configuration;
     */

    @Override
    public User login(HttpServletRequest request, UserDto userDto) {
        if (userDto.getStudentId() == null || userDto.getPassword() == null || userDto
                .getVerifyCode() == null) {
            return null;
        } else {
            User user = userDao.login(userDto.getStudentId(), userDto.getPassword());
            if (user != null) {
                Jedis jedis = new Jedis("127.0.0.1", 6379);
                //生成token
                String token = TokenUtil.generateToken(userDto.getStudentId().toString(), userDto.getPassword());
                request.getSession().setAttribute("token", token);
                request.getSession().setAttribute("studentId",user.getStudentId());
                jedis.set(userDto.getStudentId().toString(), token);
                //设置key生存时间，当key过期时，它会被自动删除，时间是秒
                jedis.expire(userDto.getStudentId().toString(), ConstantKit.TOKEN_EXPIRE_TIME);
                jedis.set(token, userDto.getStudentId().toString());
                jedis.expire(token, ConstantKit.TOKEN_EXPIRE_TIME);
                Long currentTime = System.currentTimeMillis();
                jedis.set(token + userDto.getStudentId(), currentTime.toString());
                //用完关闭
                jedis.close();
                return user;
            } else {
                return null;
            }
        }
    }

    @Override
    public Boolean addUser(String userName, String password, Integer studentId, String mail, String major, List<Integer> directions) {
        if (userDao.selectUserByStudentId(studentId)!=null){
            return false;
        }else {
            Boolean result1 = userDao.addUser(userName, password, studentId,mail,major);
            List<userDirection> list = new ArrayList<>();
            for(Integer i:directions){
                list.add(new userDirection(studentId,i));
            }
            Boolean result2 = userDao.insertDirection(list);
            if (result1&&result2){
                User user = userDao.selectUserByStudentId(studentId);
                user.setIdentifier(3);
                return true;
            }else {
                return false;
            }
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
    public Boolean sendMail(HttpServletRequest request) {
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
            Integer studentId = (Integer)request.getSession().getAttribute("studentId");
            User user = userDao.selectUserByStudentId(studentId);
            Object[] objects = VerifyUtil.createImage();
            request.getSession().setAttribute("mailVerifyCode",objects[0]);
            helper.setFrom(from);
            helper.setTo(user.getMail());
            helper.setSubject("验证码");
            String content = "<html>\n"+
                    "<body>\n" +
                    "<h1 style=\"color: red\">这是你的验证码：</h1>"+objects[0]+
                    "</body>\n"+
                    "</html>";
            helper.setText(content,true);
            javaMailSender.send(mimeMessage);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public Object verifycode(HttpServletRequest request, HttpServletResponse response) {
        try {
            Object[] objects = VerifyUtil.createImage();
            request.getSession().setAttribute("verifyCode", objects[0]);
            BufferedImage image = (BufferedImage) objects[1];
            response.setContentType("image/png");
            OutputStream os = response.getOutputStream();
            ImageIO.write(image, "png", os);
            os.close();
            return objects[0];
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean updatePassword(HttpServletRequest request, String password,String mailVerifyCode) {
        Integer studentId = (Integer) request.getSession().getAttribute("studentId");
        if (mailVerifyCode.equals(request.getSession().getAttribute("mailVerifyCode")) && userDao.selectUserByStudentId(studentId)!=null){
            Boolean result = userDao.updatePassword(studentId,password);
            if (result){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    @Override
    public Boolean updateInfo(HttpServletRequest request, String major, String userName, String headUrl, List<Integer> directions) {
        Integer studentId = (Integer) request.getSession().getAttribute("studentId");
        if (userDao.selectUserByStudentId(studentId)!=null){
            Boolean result1 = userDao.updateInfo(studentId,major,userName,headUrl);
            Boolean result2 = userDao.deleteDirection(studentId);
            List<userDirection> list = new ArrayList<>();
            for(Integer i:directions){
                list.add(new userDirection(studentId,i));
            }
            Boolean result3 = userDao.insertDirection(list);
            if (result1&&result2&&result3){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    @Override
    public UserPo displayInfo(HttpServletRequest request) {
        Integer studentId = (Integer) request.getSession().getAttribute("studentId");
        User user = userDao.selectUserByStudentId(studentId);
        if (user!=null){
            user.setDirections(userDao.displayDirection(studentId));
            UserPo userPo = new UserPo();
            userPo.setHeadUrl(user.getHeadUrl());
            userPo.setStudentId(user.getStudentId());
            userPo.setDirections(user.getDirections());
            userPo.setUserName(user.getUserName());
            userPo.setMajor(user.getMajor());
            return userPo;
        }else {
            return null;
        }
    }


}
