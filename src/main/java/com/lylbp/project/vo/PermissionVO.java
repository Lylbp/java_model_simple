package com.lylbp.project.vo;

import com.lylbp.project.entity.Permission;
import lombok.Data;
import io.swagger.annotations.*;

import java.util.Objects;

/**
 * <p>
 * 权限管理-权限表 VO类
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
@Data
@ApiModel(value = "PermissionVO对象")
public class PermissionVO extends Permission {
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Permission that = (Permission) o;
        return Objects.equals(getPermissionUrl(), that.getPermissionUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.getPermissionUrl());
    }
}