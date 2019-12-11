package com.example.SSO.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author HanSiyue
 * @Date 2019/12/2 下午10:38
 */
@RestController
public class Test {

    @PostMapping("test")
    public void test(HttpServletRequest request, HttpServletResponse response){
        System.out.println(request.getSession().getId());
        request.getSession().setAttribute("test","dsadasddsadsada");
    }

    @PostMapping("qwe")
    public void ops(HttpServletRequest request,HttpServletResponse response){
        System.out.println(request.getSession().getId());
        System.out.println(request.getSession().getAttribute("test"));
    }

}
