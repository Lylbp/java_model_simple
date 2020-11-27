package com.lylbp.manger.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * @Author weiwenbin
 * @Date 2020/6/30 下午4:43
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class PermissionAuthority implements GrantedAuthority {
    private String permissionUrl;

    @Override
    public String getAuthority() {
        return permissionUrl;
    }
}
