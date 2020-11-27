package com.lylbp.project.vo;

import com.lylbp.project.entity.MenuRole;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.*;

/**
 * <p>
 * 权限管理-菜单与角色关系表 VO类
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="MenuRoleVO对象")
public class MenuRoleVO extends MenuRole {
}