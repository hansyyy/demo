package com.example.SSO.controller;

import com.example.SSO.annotation.AuthToken;
import com.example.SSO.domain.dto.UserDto;
import com.example.SSO.domain.entity.Result;
import com.example.SSO.domain.entity.User;
import com.example.SSO.service.UserService;
import com.example.SSO.util.FileUtil;
import com.example.SSO.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * @Author HanSiyue
 * @Date 2019/9/18 下午3:32
 */
@RestController
@RequestMapping("userSystem")
@Api("工作室主页文档接口")
public class UserController {

    //相对地址
    public final static String RELATIVE_PATH="/Users/mac/Documents/demo/src/main/resources/static/img/";

    @Autowired
    private UserService userService;

    @PostMapping("login")
    @ApiOperation("用户登陆")
    public Result login(HttpServletRequest request, @RequestBody UserDto userDto) {
        try {
            System.out.println("登陆"+request.getSession().getId());
            System.out.println(userDto);
            String verifyCode = (String) request.getSession().getAttribute("verifyCode");
            if (verifyCode.equals(userDto.getVerifyCode())) {
                User user = userService.login(request,userDto);
                return ResultUtil.success(user.getUserName());
            }else {
                return ResultUtil.error();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    @PostMapping("login2")
    @ApiOperation("用户登陆2")
    public Result login2(HttpServletRequest request, @RequestParam(value = "password")String password, @RequestParam(value = "studentId")Integer studentId) {
        try {
            User user = userService.login2(request,password,studentId);
            return ResultUtil.success(user.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    @PostMapping("addUser")
    @ApiOperation("注册")
    public Result addUser(@RequestParam(value = "userName") String userName, @RequestParam(value = "password")String password, @RequestParam(value = "studentId")Integer studentId, @RequestParam(value = "mail")String mail, @RequestParam(value = "major")String major, @RequestParam(value = "directions")List<Integer> directions){
        try {
            if (userName==null||password==null){
                return ResultUtil.isNull();
            }else {
                Boolean result = userService.addUser(userName, password,studentId,mail,major,directions);
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

    @GetMapping("createVerifyCode")
    @ApiOperation("生成验证码")
    public void verifycode(HttpServletRequest request, HttpServletResponse response){
        try {
            userService.verifycode(request,response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "test", method = RequestMethod.GET)
    @ApiOperation("test")
    public Result test(HttpServletRequest request){
        if (request.getSession().getAttribute("token")==null){
            return ResultUtil.isNull();
        }else {
            return ResultUtil.success();
        }
    }

    @GetMapping("loginout")
    @ApiOperation("登出")
    @AuthToken
    public Result loginout(HttpServletResponse response,HttpServletRequest request) throws IOException {
        request.getSession().setAttribute("token",null);
        //response.sendRedirect("");
        return ResultUtil.success();
    }

    @GetMapping("sendMail")
    @ApiOperation("发送邮件")
    @AuthToken
    public Result sendMail(HttpServletRequest request){
        try {
            if (userService.sendMail(request)){
                return ResultUtil.success(request.getSession().getAttribute("mailVerifyCode"));
            }else{
                return ResultUtil.error();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    @PostMapping("updatePassword")
    @ApiOperation("修改密码")
    @AuthToken
    public Result updatePassword(HttpServletRequest request,@RequestParam(value = "password")String password,@RequestParam(value = "mailVerifyCode")String mailVerifyCode){
        try {
            if (mailVerifyCode == null || password == null) {
                return ResultUtil.isNull();
            } else {
                if (userService.updatePassword(request, password,mailVerifyCode)) {
                    return ResultUtil.success();
                } else {
                    return ResultUtil.error();
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

    @PostMapping("updateInfo")
    @ApiOperation("修改个人资料")
    @ApiImplicitParams({
            @ApiImplicitParam(allowEmptyValue = true)
    })
    @AuthToken
    public  Result updateInfo(HttpServletRequest request, @RequestParam(value = "major")String major, @RequestParam(value = "userName")String userName, @RequestParam(value = "file") MultipartFile imageFile, @RequestParam(value = "directions")List<Integer> directions){
        try {
            if (request.getSession().getAttribute("studentId")==null&&imageFile.isEmpty()){
                return ResultUtil.isNull();
            }else {
                String headUrl=FileUtil.upload(imageFile,RELATIVE_PATH);
                Boolean result = userService.updateInfo(request, major, userName, headUrl, directions);
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

    @GetMapping("displayInfo")
    @ApiOperation("展示个人资料")
    @AuthToken
    public Result displayInfo(HttpServletRequest request){
        try{
            if (request.getSession().getAttribute("studentId")==null){
                return ResultUtil.isNull();
            }else {
                return ResultUtil.success(userService.displayInfo(request));
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error();
        }
    }

}


