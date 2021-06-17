package com.lylbp.project.service;

import com.lylbp.project.entity.SecurityUser;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录相关
 *
 * @author weiwenbin
 * @date 2020/6/30 下午5:27
 */
public interface AuthService {
    /**
     * 登录
     *
     * @param username 登录名称
     * @param pwd      密码
     * @return SecurityUser
     */
    SecurityUser login(String username, String pwd);

    /**
     * 从redis中获取token
     *
     * @param auth 请求头key
     * @return token
     */
    String getRedisToken(String auth);

    /**
     * 通过请求获取SecurityUser
     *
     * @param request 请求实体
     * @return SecurityUser
     */
    SecurityUser getUserFromRequest(HttpServletRequest request);
}
