package com.lylbp.project.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.lylbp.common.enums.ResResultEnum;
import com.lylbp.core.exception.ResResultException;

import com.lylbp.project.entity.SecurityUser;
import com.lylbp.project.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author weiwenbin
 * @Date: 2020/11/26 下午1:14
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    public SecurityUser login(String username, String pwd) {
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, pwd));
        } catch (Exception e) {
            ResResultEnum resResultEnum = e instanceof DisabledException ? ResResultEnum.ACCOUNT_DISABLE : ResResultEnum.ACCOUNT_LOGIN_ERR;
            throw new ResResultException(resResultEnum);
        }

        if (!ObjectUtil.isEmpty(authentication)) {
            return (SecurityUser) authentication.getPrincipal();
        }

        throw new ResResultException(ResResultEnum.NO_LOGIN);
    }
}
