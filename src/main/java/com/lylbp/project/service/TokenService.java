package com.lylbp.project.service;//package com.lylbp.project.service;
//
///**
// * @author weiwenbin
// * @date 2020/7/1 上午11:31
// */
//public interface TokenService {
//    /**
//     * 通过登录信息创建token
//     *
//     * @param object     对象
//     * @param expireTime 过期时间秒
//     * @return String
//     */
//    String createToken(Object object, long expireTime);
//
//    /**
//     * 获取从头信息token
//     *
//     * @param headerName 头信息名称
//     * @return Boolean
//     */
//    String getTokenFromHeader(String headerName);
//
//    /**
//     * 从请求头验证token
//     *
//     * @param headerName 头信息名称
//     * @return Boolean
//     */
//    Boolean verifyTokenFromHeader(String headerName);
//
//    /**
//     * 验证token
//     *
//     * @param token token值
//     * @return Boolean
//     */
//    Boolean verifyToken(String token);
//
//
//    /**
//     * 从Token获取信息
//     *
//     * @param token token
//     * @param clazz 类
//     * @return
//     */
//    <T> T getClassByToken(String token, Class<T> clazz);
//
//    /**
//     * 从请求头解析token信息
//     *
//     * @return
//     */
//    <T> T getClassFromHeader(Class<T> clazz, String headerName);
//}
