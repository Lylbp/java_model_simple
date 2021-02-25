package com.lylbp.project.vo;

import com.lylbp.project.entity.Permission;
import com.lylbp.project.entity.Role;
import com.lylbp.project.entity.RolePermission;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author weiwenbin
 * @date 2020/5/11 下午2:22
 */
@Data
public class RoleAndPermissionVO extends RolePermission {
    @ApiModelProperty("角色信息")
    private Role role;

    @ApiModelProperty("权限信息")
    private Permission permission;
}
