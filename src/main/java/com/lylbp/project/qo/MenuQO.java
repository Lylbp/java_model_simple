package com.lylbp.project.qo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.*;

/**
 * <p>
 * 权限管理-菜单表 QO类
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MenuQO对象", description = "注意！！当该对象作为body传参时，对象名称并不是key,直接将对象值json字符串放入body实体即可！！")
public class MenuQO {
    @ApiModelProperty(value = "菜单父级 传空串此条件不生效", required = true)
    private String menuPid;
}