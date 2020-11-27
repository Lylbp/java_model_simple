package com.lylbp.project.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

import com.lylbp.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 权限管理-菜单表
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_rbac_menu")
@ApiModel(value = "Menu对象", description = "权限管理-菜单表 ")
public class Menu extends BaseEntity<Menu> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单id")
    @TableId(value = "menu_id", type = IdType.ASSIGN_UUID)
    private String menuId;

    @ApiModelProperty(value = "菜单上级id")
    @TableField("menu_pid")
    private String menuPid;

    @ApiModelProperty(value = "菜单名称")
    @TableField("menu_name")
    private String menuName;

    @ApiModelProperty(value = "前端url")
    @TableField("menu_url")
    private String menuUrl;

    @Override
    protected Serializable pkVal() {
        return this.menuId;
    }

}
