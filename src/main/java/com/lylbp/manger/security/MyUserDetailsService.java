package com.lylbp.manger.security;

import com.lylbp.project.entity.SecurityUser;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * @Author weiwenbin
 * @Date: 2020/11/25 下午7:40
 */
public interface MyUserDetailsService extends UserDetailsService {
    /**
     * 通过token获取SecurityUser
     *
     * @param request 请求
     * @return SecurityUser
     */
    SecurityUser token2SecurityUser(HttpServletRequest request);

    /**
     * 获取所有权限
     *
     * @param request 请求
     * @return Collection<ConfigAttribute>
     */
    Collection<ConfigAttribute> getConfigAttributes(HttpServletRequest request);
}
