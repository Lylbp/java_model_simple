package com.lylbp.project.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @Author weiwenbin
 * @Date 2020/7/1 下午10:03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "RoleAssignMenuQO", description = "注意！！当该对象作为body传参时，对象名称并不是key,直接将对象值json字符串放入body实体即可！！")
public class RoleAssignMenuQO {
    @ApiModelProperty(value = "角色id 此参数必须传", required = true)
    @NotBlank
    private String roleId;
}
