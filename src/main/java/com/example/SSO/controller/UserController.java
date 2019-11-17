package com.example.SSO.controller;

import com.example.SSO.annotation.AuthToken;
import com.example.SSO.domain.dto.UserDto;
import com.example.SSO.domain.entity.Result;
import com.example.SSO.domain.entity.User;
import com.example.SSO.service.UserService;
import com.example.SSO.util.FileUtil;
import com.example.SSO.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * @Author HanSiyue
 * @Date 2019/9/18 下午3:32
 */
@EnableRedisHttpSession
@RestController
@RequestMapping("userSystem")
@Api("工作室主页文档接口")
public class UserController {

    //@Value("${path}")
    private static final String path="/Users/mac/Desktop/pic";

    @Autowired
    private UserService userService;

    @PostMapping("login")
    @ApiOperation("用户登陆")
    public Result login(HttpServletRequest request, @RequestBody UserDto userDto) {
        try {
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

    @PostMapping("addUser")
    @ApiOperation("注册")
    public Result addUser(String userName, String password, Integer studentId, String mail, String major, @RequestParam(value = "directions")List<Integer> directions){
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

    @PostMapping("createVerifyCode")
    @ApiOperation("生成验证码")
    public void verifycode(HttpServletRequest request, HttpServletResponse response){
        try {
            System.out.println(userService.verifycode(request,response));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "test", method = RequestMethod.GET)
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
        return ResultUtil.success();
    }

    @PostMapping("sendMail")
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
    public Result updatePassword(HttpServletRequest request,String password,String mailVerifyCode){
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
    @AuthToken
    public  Result updateInfo(HttpServletRequest request, String major, String userName, @RequestParam(value = "file") MultipartFile imageFile, @RequestParam(value = "directions")List<Integer> directions){
        try {
            if (request.getSession().getAttribute("studentId")==null&&imageFile.isEmpty()){
                return ResultUtil.isNull();
            }else {
                FileUtil.upload(imageFile,path);
                Boolean result = userService.updateInfo(request,major,userName,FileUtil.fileUrl(imageFile,path),directions);
                if (result){
                    return ResultUtil.success(FileUtil.fileUrl(imageFile,path));
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


