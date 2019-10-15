package com.example.SSO.controller;

import com.example.SSO.annotation.AuthToken;
import com.example.SSO.constant.ConstantKit;
import com.example.SSO.domain.dto.UserDto;
import com.example.SSO.domain.entity.Result;
import com.example.SSO.domain.entity.User;
import com.example.SSO.service.UserService;
import com.example.SSO.util.ResultUtil;
import com.example.SSO.util.TokenUtil;
import com.example.SSO.util.VerifyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author HanSiyue
 * @Date 2019/9/18 下午3:32
 */
@EnableRedisHttpSession
@RestController
@RequestMapping("userSystem")
@Api("登陆文档接口")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    @ApiOperation("用户登陆")
    public Result login(HttpServletRequest request, @RequestBody UserDto userDto) {
        try {
            String verifyCode = (String)request.getSession().getAttribute("verifyCode");
            if (verifyCode.equals(userDto.getVerifyCode())){
                User user = userService.login(userDto);
                if (user!=null){
                    Jedis jedis = new Jedis("127.0.0.1", 6379);
                    //生成token
                    String token = TokenUtil.generateToken(userDto.getUserName(), userDto.getPassword());
                    request.getSession().setAttribute("token",token);
                    request.getSession().setAttribute("userName",user.getUserName());
                    jedis.set(userDto.getUserName(), token);
                    //设置key生存时间，当key过期时，它会被自动删除，时间是秒
                    jedis.expire(userDto.getUserName(), ConstantKit.TOKEN_EXPIRE_TIME);
                    jedis.set(token, userDto.getUserName());
                    jedis.expire(token, ConstantKit.TOKEN_EXPIRE_TIME);
                    Long currentTime = System.currentTimeMillis();
                    jedis.set(token + userDto.getUserName(), currentTime.toString());
                    //用完关闭
                    jedis.close();
                    return ResultUtil.success("用户："+user.getUserName()+"已登陆！"+"token:"+token);
                }else {
                    return ResultUtil.notExist("用户不存在");
                }
            }else {
                return ResultUtil.error("验证码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("登陆失败");
        }
    }

    @PostMapping("addUser")
    @ApiOperation("注册")
    public Result addUser(String userName, String password, String studentId, String mail, String major){
        try {
            if (userName==null||password==null){
                return ResultUtil.isNull("用户名或密码为空");
            }else {
                Boolean result = userService.addUser(userName, password,studentId,mail,major);
                if (result){
                    return ResultUtil.success("用户"+userName+"已注册");
                    }else {
                    return ResultUtil.error("注册失败");
                    }
                }
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error("注册失败");
        }
    }

    @PostMapping("createVerifyCode")
    @ApiOperation("生成验证码")
    public void verifycode(HttpServletRequest request, HttpServletResponse response){
        try {
            Object[] objects = VerifyUtil.createImage();
            request.getSession().setAttribute("verifyCode",objects[0]);
            Map map = new HashMap<>();
            map.put("base64","data:image/png;base64,"+ VerifyUtil.getbase64(objects[1]));
            map.put("verifycode",objects[0]);
            BufferedImage image = (BufferedImage) objects[1];
            response.setContentType("image/png");
            OutputStream os = response.getOutputStream();
            ImageIO.write(image, "png", os);
            System.out.println(map);
            os.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @GetMapping("test")
    @ApiOperation("test")
    @AuthToken
    public Result test(){
        return ResultUtil.success();
    }

    @GetMapping("loginout")
    @ApiOperation("登出")
    @AuthToken
    public Result loginout(HttpServletResponse response,HttpServletRequest request) throws IOException {
        request.getSession().setAttribute("token",null);
        //response.sendRedirect("");
        return ResultUtil.success("用户已登出");
    }

    @PostMapping("sendMail")
    @ApiOperation("发送邮件")
    @AuthToken
    public Result sendMail(HttpServletRequest request, String subject, String context){
        try {
            String userName = (String)request.getSession().getAttribute("userName");
            User user = userService.selectUserByUserName(userName);
            String to=user.getMail();
            if (userService.sendMail(to, subject, context)){
                return ResultUtil.success("邮件已发送！");
            }else{
                return ResultUtil.error("邮件发送失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error("错误！");
        }
    }

}


