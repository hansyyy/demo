package com.example.SSO.domain.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author HanSiyue
 * @Date 2019/10/9 下午9:11
 */
@Data
@ApiModel
public class UserPo {
    @ApiModelProperty(name = "userName", value = "用户名", dataType = "string")
    private String userName;
}
