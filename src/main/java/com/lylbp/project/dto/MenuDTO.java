package com.lylbp.project.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 菜单表DTO对象
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MenuDTO", description = "注意！！当该对象作为body传参时，对象名称并不是key,直接将对象值json字符串放入body实体即可！！")
public class MenuDTO {
    @ApiModelProperty(value = "菜单id 添加传空串")
    @Length(max = 32)
    private String menuId;

    @ApiModelProperty(value = "菜单名称", required = true)
    @NotBlank
    @Length(max = 32)
    private String menuName;

    @ApiModelProperty(value = "前端菜单路由", required = true)
    @NotBlank
    @Length(max = 32)
    private String menuUrl;

    @ApiModelProperty(value = "父路由id", required = true)
    @Length(max = 32)
    private String menuPid;
}