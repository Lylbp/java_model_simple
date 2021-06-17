package com.lylbp.project.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.lylbp.common.constant.ProjectConstant;
import com.lylbp.common.enums.ResResultEnum;
import com.lylbp.common.exception.ResResultException;
import com.lylbp.common.utils.TokenUtil;
import com.lylbp.manager.redis.service.RedisService;
import com.lylbp.project.entity.SecurityUser;
import com.lylbp.project.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 登录相关
 *
 * @author weiwenbin
 * @date 2020/6/30 下午5:31
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisService redisService;

    @Override
    public SecurityUser login(String username, String pwd) {
        Authentication authentication;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, pwd));
        } catch (Exception e) {
            ResResultEnum resResultEnum = e instanceof DisabledException
                    ? ResResultEnum.ACCOUNT_DISABLE : ResResultEnum.ACCOUNT_LOGIN_ERR;
            throw new ResResultException(resResultEnum);
        }

        if (!ObjectUtil.isEmpty(authentication)) {
            return (SecurityUser) authentication.getPrincipal();
        }

        throw new ResResultException(ResResultEnum.NO_LOGIN);
    }

    @Override
    public String getRedisToken(String auth){
        //获取redis中的token
        String redisKey = ProjectConstant.REDIS_USER_TOKEN_PRE + auth;
        return redisService.strGet(redisKey);
    }

    @Override
    public SecurityUser getUserFromRequest(HttpServletRequest request) {
        //获取redis中的token
        String token = getRedisToken(request.getHeader(ProjectConstant.AUTHENTICATION));
        if (StrUtil.isEmpty(token)){
            throw new ResResultException(ResResultEnum.NO_LOGIN);
        }

        //token转SecurityUser
        SecurityUser securityUser = TokenUtil.getClazzByToken(token, SecurityUser.class);
        if (null == securityUser) {
            throw new ResResultException(ResResultEnum.NO_LOGIN);
        }

        return securityUser;
    }
}
