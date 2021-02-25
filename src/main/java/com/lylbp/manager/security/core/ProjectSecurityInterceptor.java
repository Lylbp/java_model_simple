package com.lylbp.manager.security.core;

import cn.hutool.core.util.ObjectUtil;
import com.lylbp.manager.security.core.config.SecurityProperties;
import com.lylbp.project.entity.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限拦截器
 *
 * @author weiwenbin
 * @date 2020/5/11 下午2:54
 */
@Component
public class ProjectSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {
    @Resource
    private SecurityProperties securityProperties;

    @Resource
    private ProjectFilterInvocationSecurityMetadataSource projectFilterInvocationSecurityMetadataSource;

    @Autowired
    public void setProjectFilterInvocationSecurityMetadataSource(ProjectAccessDecisionManager projectAccessDecisionManager) {
        super.setAccessDecisionManager(projectAccessDecisionManager);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        Boolean securityEnabled = securityProperties.getEnabled();
        List<String> allowApi = securityProperties.getAllowApi();
        List<String> allowStatic = securityProperties.getAllowStatic();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        FilterInvocation fi = new FilterInvocation(servletRequest, servletResponse, filterChain);

        //OPTIONS请求直接放行
        if (request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            return;
        }

        //security未开启直接放行
        if (!securityEnabled) {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            return;
        }

        //白名单请求直接放行
        List<String> white = new ArrayList<>();
        white.addAll(allowApi);
        white.addAll(allowStatic);
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String path : white) {
            if (pathMatcher.match(path, request.getRequestURI())) {
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
                return;
            }
        }

        //超级管理员直接放行
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication) {
            if (authentication.getPrincipal() instanceof SecurityUser) {
                SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
                if (securityUser.getIsSupperAdmin()) {
                    fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
                    return;
                }
            }
        }

        if (ObjectUtil.isNotEmpty(fi)) {
            /*
              beforeInvocation内部会调用FilterInvocationSecurityMetadataSource中getAttributes获取访问当前路由需要的权限
              然后调用AccessDecisionManager中的decide方法进行鉴权操作
             */
            InterceptorStatusToken token = super.beforeInvocation(fi);
            try {
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            } finally {
                super.afterInvocation(token, null);
            }
        }
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return projectFilterInvocationSecurityMetadataSource;
    }
}
