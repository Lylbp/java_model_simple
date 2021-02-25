package com.lylbp.project.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.lylbp.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 权限管理-菜单与角色关系表
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_rbac_menu_role")
@ApiModel(value = "MenuRole对象", description = "权限管理-菜单与角色关系表 ")
public class MenuRole extends BaseEntity<MenuRole> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单与角色关系id")
    @TableId(value = "menu_role_id", type = IdType.ASSIGN_UUID)
    private String menuRoleId;

    @ApiModelProperty(value = "菜单id")
    @TableField("menu_id")
    private String menuId;

    @ApiModelProperty(value = "角色id")
    @TableField("role_id")
    private String roleId;

    @Override
    protected Serializable pkVal() {
        return this.menuRoleId;
    }

}
