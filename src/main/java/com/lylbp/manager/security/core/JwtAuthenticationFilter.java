package com.lylbp.manager.security.core;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lylbp.common.enums.ResResultEnum;
import com.lylbp.common.utils.ResResultUtil;
import com.lylbp.common.utils.ResponseUtil;
import com.lylbp.manager.security.interfaces.MyUserDetailsService;
import com.lylbp.manager.security.core.config.SecurityProperties;
import com.lylbp.project.entity.SecurityUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * token验证
 *
 * @author weiwenbin
 * @date 2020/9/3 上午10:22
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Resource
    private SecurityProperties securityProperties;

    @Resource
    private MyUserDetailsService myUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException, AuthenticationException {
        //当前请求uri
        String requestURI = request.getRequestURI();
        //白名单请求直接放行
        List<String> white = new ArrayList<>();
        white.addAll(securityProperties.getAllowApi());
        white.addAll(securityProperties.getAllowStatic());
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String path : white) {
            if (pathMatcher.match(path, requestURI)) {
                chain.doFilter(request, response);
                return;
            }
        }

        //token转SecurityUser
        SecurityUser securityUser = myUserDetailsService.token2SecurityUser(request);
        if (ObjectUtil.isEmpty(securityUser)) {
            ResponseUtil.outJson(
                    response, JSON.toJSONString(ResResultUtil.makeRsp(ResResultEnum.NO_LOGIN, new Object()),
                            SerializerFeature.WriteMapNullValue)
            );
            return;
        }

        //若安全验证开放
        if (securityProperties.getEnabled()) {
            //设置权限
            Collection<? extends GrantedAuthority> authorities = securityUser.getAuthorities();
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    securityUser,
                    null,
                    authorities);

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            //设置用户登录状态
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}
