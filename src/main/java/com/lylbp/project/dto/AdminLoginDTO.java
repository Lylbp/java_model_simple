package com.lylbp.project.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 后台用户登录DTO对象
 * </p>
 *
 * @author weiwenbin
 * @date 2020/7/1 下午2:40
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AdminLoginDTO", description = "注意！！当该对象作为body传参时，对象名称并不是key,直接将对象值json字符串放入body实体即可！！")

public class AdminLoginDTO {
    @ApiModelProperty(value = "账号", required = true)
    @NotBlank
    private String loginName;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank
    private String pwd;
}
