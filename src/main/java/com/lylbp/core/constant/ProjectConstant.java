package com.lylbp.core.constant;


public class ProjectConstant {
    /**
     * HTTP Request中token在header中的key值
     */
    public static final String AUTHENTICATION = "Authorization";

    /**
     * token过期时间，单位：秒
     */
    public static final long JWT_EXPIRE_TIME = 24 * 60 * 60;
}
