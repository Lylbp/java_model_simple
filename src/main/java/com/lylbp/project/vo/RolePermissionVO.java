package com.lylbp.project.vo;

import com.lylbp.project.entity.RolePermission;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.*;

/**
 * <p>
 * 权限管理-角色与权限关系表 VO类
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="RolePermissionVO对象")
public class RolePermissionVO extends RolePermission {
}