package com.lylbp.project.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.lylbp.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 权限管理-管理员与角色关系表
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_rbac_admin_role")
@ApiModel(value = "AdminRole对象", description = "权限管理-管理员与角色关系表 ")
public class AdminRole extends BaseEntity<AdminRole> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "管理员与角色关系id")
    @TableId(value = "admin_role_id", type = IdType.ASSIGN_UUID)
    private String adminRoleId;

    @ApiModelProperty(value = "管理员id")
    @TableField("admin_id")
    private String adminId;

    @ApiModelProperty(value = "角色id")
    @TableField("role_id")
    private String roleId;

    @Override
    protected Serializable pkVal() {
        return this.adminRoleId;
    }

}
