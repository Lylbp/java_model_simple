package com.lylbp.core.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 跨域拦截器
 *
 * @author weiwenbi
 */
@Slf4j
public class NewCrossDomainInterceptor implements HandlerInterceptor {

    /**
     * Pre handler.
     * 拦截每个请求     在请求处理之前进行调用（Controller方法调用之前
     *
     * @param request  the request
     * @param response the response
     * @param handler  the handler
     * @return the boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        response.setHeader("Access-control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Accept, Content-Type, Authorization");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String options = HttpMethod.OPTIONS.toString().toUpperCase();
        String method = request.getMethod().toUpperCase();
        if (options.equals(method)) {
            response.setStatus(HttpStatus.OK.value());
            return false;
        }
        return true;
    }

}