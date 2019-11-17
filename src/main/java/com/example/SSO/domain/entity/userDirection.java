package com.example.SSO.domain.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author HanSiyue
 * @Date 2019/11/17 下午2:31
 */

@Data
public class userDirection {
    private Integer direction;
    private Integer studentId;

    public userDirection(Integer studentId,Integer direction){
        this.direction=direction;
        this.studentId=studentId;
    }
}
