package com.lylbp.project.vo;

import com.lylbp.project.entity.AdminRole;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.*;

/**
 * <p>
 * 权限管理-管理员与角色关系表 VO类
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="AdminRoleVO对象")
public class AdminRoleVO extends AdminRole {
}