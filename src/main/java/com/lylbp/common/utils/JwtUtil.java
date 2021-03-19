package com.lylbp.common.utils;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * jwt工具类
 *
 * @author weiwenbin
 * @date 2020-2-12 10:43
 */
@Slf4j
public class JwtUtil {
    public static final String TOKEN_SECRET = "1qaz2wsx";
    public static final String TOKEN_CLAIM_NAME = "token";

    /**
     * 建立token
     *
     * @param jsonStr    json字符串
     * @param expireTime 过期时长
     * @return String
     */
    public static String createToken(String jsonStr, Long expireTime) {
        Date date = new Date(System.currentTimeMillis() + expireTime);
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        return JWT.create()
                .withClaim(TOKEN_CLAIM_NAME, jsonStr)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /**
     * 验证token是否有效
     *
     * @param token token字符串
     * @return DecodedJWT
     */
    public static DecodedJWT verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(token);
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    /**
     * 获取jwt过期时间
     *
     * @param token token
     * @return Date
     */
    public static Date getExp(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getExpiresAt();
    }

    /**
     * 获取token令牌对应对象
     *
     * @param token token字符串
     * @param clazz 要转换的class
     * @return T
     */
    public static <T> T getTokenClaimsObj(String token, Class<T> clazz) {
        DecodedJWT decode = JWT.decode(token);
        Claim claim = decode.getClaim(TOKEN_CLAIM_NAME);
        String claimStr = claim.asString();
        if (clazz == String.class) {
            return (T) claimStr;
        } else {
            return JSON.parseObject(claim.asString(), clazz);
        }
    }
}
