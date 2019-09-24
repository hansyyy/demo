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
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
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
                Jedis jedis = new Jedis("127.0.0.1", 6379);
                String token = TokenUtil.generateToken(userDto.getUserName(), userDto.getPassword());
                jedis.set(userDto.getUserName(), token);
                //设置key生存时间，当key过期时，它会被自动删除，时间是秒
                jedis.expire(userDto.getUserName(), ConstantKit.TOKEN_EXPIRE_TIME);
                jedis.set(token, userDto.getUserName());
                jedis.expire(token, ConstantKit.TOKEN_EXPIRE_TIME);
                Long currentTime = System.currentTimeMillis();
                jedis.set(token + userDto.getUserName(), currentTime.toString());

                //用完关闭
                jedis.close();
                Map map = new HashMap<>();
                map.put("token",token);
                map.put("user",user);
                return ResultUtil.success(map);
            }else {
                return ResultUtil.error();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    @PostMapping("addUser")
    @ApiOperation("注册")
    public Result addUser(String userName, String password){
        try {
            if (userName==null||password==null||userService.selectUserByUserName(userName)==null){
                return ResultUtil.isNull();
            }else {
                Boolean result = userService.addUser(userName, password);
                if (result){
                    return ResultUtil.success();
                }else {
                    return ResultUtil.error();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    @PostMapping("createVerifyCode")
    @ApiOperation("生成验证码")
    public Result verifycode(HttpServletRequest request){
        try {
            Object[] objects = VerifyUtil.createImage();
            request.getSession().setAttribute("verifyCode",objects[0]);
            Map map = new HashMap<>();
            map.put("base64","data:image/png;base64,"+ VerifyUtil.getbase64(objects[1]));
            map.put("verifycode",objects[0]);
            return ResultUtil.success(map);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    @GetMapping("test")
    @ApiOperation("test")
    @AuthToken
    public Result test(){
        return ResultUtil.success();
    }


}