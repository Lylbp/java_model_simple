package com.lylbp.core.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 * @Author weiwenbin
 * @Date 2020-03-13 12:09
 */
public class ApiInterceptor implements HandlerInterceptor {
    /**
     * 请求前调用
     *
     * @param request  请求
     * @param response 响应
     * @param handler  请求类对象
     * @return boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return true;
    }

    /**
     * 请求成功后调用，失败则不调用
     *
     * @param request      请求
     * @param response     响应
     * @param handler      请求类对象
     * @param modelAndView 模型与视图对象
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    /**
     * @param request  请求
     * @param response 响应
     * @param handler  请求类对象
     * @param ex       异常
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }
}
