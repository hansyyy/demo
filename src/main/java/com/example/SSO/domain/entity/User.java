package com.example.SSO.domain.entity;

import lombok.Data;

/**
 * @Author HanSiyue
 * @Date 2019/9/18 下午3:34
 */
@Data
public class User {
    private Integer id;
    private String userName;
    private String password;
}