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
 * 后台管理员表
 * </p>
 *
 * @author weiwenbin
 * @since 2020-07-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_s_admin")
@ApiModel(value = "Admin对象", description = "后台管理员表")
public class Admin extends BaseEntity<Admin> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "管理员id")
    @TableId(value = "admin_id", type = IdType.ASSIGN_UUID)
    private String adminId;

    @ApiModelProperty(value = "登录账号")
    @TableField("login_account")
    private String loginAccount;

    @ApiModelProperty(value = "真实名称")
    @TableField("real_name")
    private String realName;

    @ApiModelProperty(value = "密码")
    @TableField("pwd")
    private String pwd;

    @ApiModelProperty(value = "账号是否启用")
    @TableField("is_enabled")
    private String isEnabled;

    @Override
    protected Serializable pkVal() {
        return this.adminId;
    }

}
