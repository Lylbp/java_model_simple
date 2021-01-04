package com.lylbp.common.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.lylbp.common.exception.ResResultException;
import com.lylbp.common.enums.ResResultEnum;

import javax.servlet.http.HttpServletRequest;

/**
 * token生成与解析工具依赖于JwtUtil
 *
 * @author weiwenbin
 * @date 2020/7/9 上午10:46
 */
public class TokenUtil {
    /**
     * 将对象加密成token
     *
     * @param object 对象
     * @return String
     */
    public static String createToken(Object object, long expireTime) {
        return JwtUtil.createToken(JSONUtil.toJsonStr(object), expireTime);
    }

    /**
     * 请求头中获取token
     *
     * @param request    请求
     * @param headerName 头名称
     * @return String
     */
    public static String getTokenFromHeader(HttpServletRequest request, String headerName) {
        String token = request.getHeader(headerName);
        if (ObjectUtil.isEmpty(token) || !verifyToken(token)) {
            throw new ResResultException(ResResultEnum.NO_LOGIN);
        }

        return token;
    }

    /**
     * 验证请求头中的token
     *
     * @param request    请求
     * @param headerName 头名称
     * @return Boolean
     */
    public static Boolean verifyTokenFromHeader(HttpServletRequest request, String headerName) {
        return verifyToken(getTokenFromHeader(request, headerName));
    }


    /**
     * 从请求头中获取对象,并转为对象
     *
     * @param request    请求
     * @param headerName 头名称
     * @param clazz      类型
     * @param <T>        类型
     * @return T
     */
    public static <T> T getClazzFromHeader(HttpServletRequest request, String headerName, Class<T> clazz) {
        return getClazzByToken(getTokenFromHeader(request, headerName), clazz);
    }

    /**
     * 验证token字符串
     *
     * @param token
     * @return
     */
    public static Boolean verifyToken(String token) {
        return !ObjectUtil.isEmpty(JwtUtil.verifyToken(token));
    }

    /**
     * 解析token成对象
     *
     * @param token token值
     * @param clazz 类型
     * @param <T>   类型
     * @return T
     */
    public static <T> T getClazzByToken(String token, Class<T> clazz) {
        T t = JwtUtil.getTokenClaimsObj(token, clazz);
        if (ObjectUtil.isEmpty(t) || !verifyToken(token)) {
            throw new ResResultException(ResResultEnum.NO_LOGIN);
        }

        return t;
    }
}
