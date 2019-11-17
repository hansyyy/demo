package com.example.SSO.domain.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author HanSiyue
 * @Date 2019/9/18 下午3:34
 */
@Data
public class User {
    private Integer identifier;
    private Integer studentId;
    private String userName;
    private String password;
    private String mail;
    private String major;
    private String headUrl;
    private List<Integer> directions;

    public User(){
        super();
    }

}