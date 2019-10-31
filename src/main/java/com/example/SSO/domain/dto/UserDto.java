package com.example.SSO.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @Author HanSiyue
 * @Date 2019/9/18 下午3:37
 */
@ApiModel
@Data
@ToString
public class UserDto {
    @ApiModelProperty(name = "studentId", value = "学号", dataType = "integer")
    private Integer studentId;
    @ApiModelProperty(name = "password", value = "密码", dataType = "string")
    private String password;
    @ApiModelProperty(name = "verifyCode", value = "验证码", dataType = "string")
    private String verifyCode;
}
