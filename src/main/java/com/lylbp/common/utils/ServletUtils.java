package com.lylbp.common.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 客户端工具类
 *
 * @author weiwenbin
 */
public class ServletUtils {
    /**
     * 是否是Ajax异步请求
     *
     * @param request 请求
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String accept = request.getHeader("accept");
        if (accept != null && accept.indexOf("application/json") != -1) {
            return true;
        }

        String xRequestedWith = request.getHeader("X-Requested-With");
        if (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1) {
            return true;
        }

        String uri = request.getRequestURI();
        if (StrUtil.equalsAnyIgnoreCase(uri, ".json", ".xml")) {
            return true;
        }

        String ajax = request.getParameter("__ajax");
        if (StrUtil.equalsAnyIgnoreCase(ajax, "json", "xml")) {
            return true;
        }
        return false;
    }

    /**
     * 给url添加host
     *
     * @param request 请求
     * @param apiUrl  url
     * @return String
     */
    public static String addHost(HttpServletRequest request, String apiUrl) {
        String httpTag = "http";
        String refererURL = request.getHeader("https-tag");
        if (ObjectUtil.isNotEmpty(refererURL)) {
            httpTag = refererURL.toLowerCase();
        }

        return httpTag + "://" + request.getServerName() + ":" + request.getServerPort() + apiUrl;
    }
}
