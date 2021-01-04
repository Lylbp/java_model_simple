package com.lylbp.project.service.impl;//package com.lylbp.project.service.impl;
//
//import cn.hutool.core.util.ObjectUtil;
//import cn.hutool.json.JSONUtil;
//import com.lylbp.common.enums.ResResultEnum;
//import com.lylbp.common.exception.ResResultException;
//import com.lylbp.common.utils.JwtUtil;
//import com.lylbp.project.service.TokenService;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//
///**
// * @author weiwenbin
// * @date 2020-06-30 18:25
// */
//@Service
//public class TokenServiceImpl implements TokenService {
//    @Resource
//    private HttpServletRequest request;
//
//    @Override
//    public String createToken(Object object, long expireTime) {
//        expireTime = expireTime * 1000;
//        return JwtUtil.createToken(JSONUtil.toJsonStr(object), expireTime);
//    }
//
//    @Override
//    public String getTokenFromHeader(String headerName) {
//        String token = request.getHeader(headerName);
//        if (ObjectUtil.isEmpty(token)) {
//            throw new ResResultException(ResResultEnum.NO_LOGIN);
//        }
//
//        return token;
//    }
//
//    @Override
//    public Boolean verifyTokenFromHeader(String headerName) {
//        return verifyToken(getTokenFromHeader(headerName));
//    }
//
//    @Override
//    public Boolean verifyToken(String token) {
//        return !ObjectUtil.isEmpty(JwtUtil.verifyToken(token));
//    }
//
//    @Override
//    public <T>T getClassByToken(String token, Class<T> clazz) {
//        return JwtUtil.getTokenClaimsObj(token, clazz);
//    }
//
//    @Override
//    public <T>T getClassFromHeader(Class<T> clazz, String headerName) {
//        String token = getTokenFromHeader(headerName);
//        T t = getClassByToken(token, clazz);
//        if (ObjectUtil.isEmpty(t)) {
//            throw new ResResultException(ResResultEnum.NO_LOGIN);
//        }
//
//        return t;
//    }
//}
