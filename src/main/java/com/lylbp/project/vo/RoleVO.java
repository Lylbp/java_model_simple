package com.lylbp.project.vo;

import com.lylbp.project.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.*;

/**
 * <p>
 * 权限管理-角色表 VO类
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="RoleVO对象")
public class RoleVO extends Role {
}