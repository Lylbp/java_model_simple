package com.lylbp.manager.security.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lylbp.common.enums.ResResultEnum;
import com.lylbp.common.utils.ResResultUtil;
import com.lylbp.common.utils.ResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 未认证用户访问需要权限的url
 * @author weiwenbin
 * @date 2020/5/11 下午11:08
 */
public class ProjectAuthenticationEntryPoint implements AuthenticationEntryPoint{
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) {
        ResponseUtil.outJson(
                response,JSON.toJSONString(ResResultUtil.makeRsp(ResResultEnum.NO_LOGIN),
                        SerializerFeature.WriteMapNullValue)
        );
    }
}
