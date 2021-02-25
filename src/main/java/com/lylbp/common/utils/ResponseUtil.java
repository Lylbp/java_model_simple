package com.lylbp.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ResponseUtil
 *
 * @author weiwenbin
 * @date 2020/5/12 上午11:46
 */
public class ResponseUtil {
    private final static Logger logger = LoggerFactory.getLogger(ResponseUtil.class);

    /**
     * 输出json
     *
     * @param response 响应
     * @param jsonStr  输出的json
     */
    public static void outJson(HttpServletResponse response, String jsonStr) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.setStatus(HttpStatus.OK.value());
        try {
            response.getWriter().write(jsonStr);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
