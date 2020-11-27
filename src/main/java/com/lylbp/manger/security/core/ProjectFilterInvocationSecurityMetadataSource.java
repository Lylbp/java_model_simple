package com.lylbp.manger.security.core;

import com.lylbp.manger.security.MyUserDetailsService;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 动态权限数据源，用于获取动态权限规则
 *
 * @Author weiwenbin
 * @Date 2020/5/11 下午2:18
 */
@Component
public class ProjectFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Resource
    private MyUserDetailsService myUserDetailsService;

    /**
     * 当前请求所需要的权限集合 若当前请求无权限要求返回null
     * 如果api未配置可访问的角色代表所有人都可以访问
     *
     * @param o object对象
     * @return Collection<ConfigAttribute>
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation) o).getHttpRequest();
        try {
            return myUserDetailsService.getConfigAttributes(request);
        } catch (Exception exception) {
            throw new AuthorizationServiceException(exception.getMessage());
        }
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
