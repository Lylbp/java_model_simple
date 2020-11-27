package com.lylbp.project.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 角色表DTO对象
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="RoleDTO", description = "注意！！当该对象作为body传参时，对象名称并不是key,直接将对象值json字符串放入body实体即可！！")
public class RoleDTO {
    @ApiModelProperty(value = "角色id 添加时传空串")
    @Length(max = 32)
    private String roleId;

    @ApiModelProperty(value = "角色名称", required = true)
    @NotBlank
    @Length(max = 128)
    private String roleName;

    @ApiModelProperty(value = "角色描述", required = true)
    @NotBlank
    @Length(max = 512)
    private String roleDescription;
}