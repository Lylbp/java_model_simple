package com.lylbp.project.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.lylbp.core.constant.ProjectConstant;
import com.lylbp.common.utils.TokenUtil;
import com.lylbp.manger.security.MyUserDetailsService;
import com.lylbp.manger.security.entity.PermissionAuthority;
import com.lylbp.project.entity.SecurityUser;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * UserDetailsService
 *
 * @Author weiwenbin
 * @Date 2020/6/30 下午5:25
 */
@Service
public class UserDetailServiceImpl implements MyUserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<PermissionAuthority> permissionAuthorities = new ArrayList<>();
        return new SecurityUser(permissionAuthorities);
    }

    @Override
    public SecurityUser token2SecurityUser(HttpServletRequest request) {
        //验证token
        String token = request.getHeader(ProjectConstant.AUTHENTICATION);
        if (ObjectUtil.isEmpty(token)) {
            return null;
        }

        Boolean verifyTokenFromHeader = TokenUtil.verifyToken(token);
        if (!verifyTokenFromHeader) {
            return null;
        }

        return TokenUtil.getClazzByToken(token, SecurityUser.class);
    }

    @Override
    public Collection<ConfigAttribute> getConfigAttributes(HttpServletRequest request) {
        return null;
    }
}
