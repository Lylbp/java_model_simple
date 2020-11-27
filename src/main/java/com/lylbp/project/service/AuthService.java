package com.lylbp.project.service;

import com.lylbp.project.entity.SecurityUser;

/**
 * 登录相关
 *
 * @Author weiwenbin
 * @Date 2020/6/30 下午5:27
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
}
