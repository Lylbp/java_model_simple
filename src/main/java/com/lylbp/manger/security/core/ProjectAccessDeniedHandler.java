package com.lylbp.manger.security.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lylbp.common.enums.ResResultEnum;
import com.lylbp.common.utils.ResResultUtil;
import com.lylbp.common.utils.ResponseUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 已认证用户访问无权限接口处理
 *
 * @Author weiwenbin
 * @Date 2020/5/11 下午11:14
 */
public class ProjectAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException){
        if (accessDeniedException instanceof AuthorizationServiceException){
            ResponseUtil.outJson(
                    response,JSON.toJSONString(ResResultUtil.makeRsp(ResResultEnum.SYSTEM_ERR.getCode(), accessDeniedException.getMessage()),
                            SerializerFeature.WriteMapNullValue)
            );
        }else {
            ResponseUtil.outJson(
                    response,JSON.toJSONString(ResResultUtil.makeRsp(ResResultEnum.NO_AUTHENTICATION),
                            SerializerFeature.WriteMapNullValue)
            );
        }

    }

}
