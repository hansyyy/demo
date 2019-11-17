package com.example.SSO.domain.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author HanSiyue
 * @Date 2019/10/9 下午9:11
 */
@Data
@ApiModel
public class UserPo {
    @ApiModelProperty(name = "userName", value = "用户名", dataType = "string")
    private String userName;
    @ApiModelProperty(name = "major", value = "专业", dataType = "string")
    private String major;
    @ApiModelProperty(name = "headUrl", value = "头像", dataType = "string")
    private String headUrl;
    @ApiModelProperty(name = "directions", value = "方向")
    private List<Integer> directions;
    @ApiModelProperty(name = "studentId", value = "学号")
    private Integer studentId;
}
