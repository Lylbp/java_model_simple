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
 * 权限管理-角色与权限关系表
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_rbac_role_permission")
@ApiModel(value = "RolePermission对象", description = "权限管理-角色与权限关系表 ")
public class RolePermission extends BaseEntity<RolePermission> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色与权限关系id")
    @TableId(value = "role_permission_id", type = IdType.ASSIGN_UUID)
    private String rolePermissionId;

    @ApiModelProperty(value = "角色id")
    @TableField("role_id")
    private String roleId;

    @ApiModelProperty(value = "权限id")
    @TableField("permission_id")
    private String permissionId;

    @Override
    protected Serializable pkVal() {
        return this.rolePermissionId;
    }

}
