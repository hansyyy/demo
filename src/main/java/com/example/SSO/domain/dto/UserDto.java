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
    @ApiModelProperty(name = "userName", value = "管理员名字", dataType = "string")
    private String userName;
    @ApiModelProperty(name = "password", value = "管理员密码", dataType = "string")
    private String password;
    @ApiModelProperty(name = "verifyCode", value = "验证码", dataType = "string")
    private String verifyCode;
}
