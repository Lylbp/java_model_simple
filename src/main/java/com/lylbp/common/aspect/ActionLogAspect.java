package com.lylbp.common.aspect;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lylbp.common.annotation.ActionLog;
import com.lylbp.common.utils.UserAgentUtil;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.Version;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 操作日志注解
 *
 * @author weiwenbin
 * @date 2020-03-29 17:20
 */
@Aspect
@Component
public class ActionLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(ActionLogAspect.class);
    ////////////////////////////////////////////////////////////

    /**
     * 定义controller切入点拦截规则，拦截ActionLog注解的方法
     */
    @Pointcut("@annotation(com.lylbp.common.annotation.ActionLog)")
    public void actionLogAspect() {

    }

    /**
     * 拦截控制层的操作日志
     *
     * @param joinPoint 切入点
     * @param actionLog 操作日志注解对象
     */
    @Before(value = "actionLogAspect() && @annotation(actionLog)")
    public void recordLog(JoinPoint joinPoint, ActionLog actionLog) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            assert attributes != null;
            HttpServletRequest request = attributes.getRequest();

            //当前时间字符串
            DateTime date = DateUtil.date();
            //请求描述
            String description = actionLog.description();
            //请求地址
            String requestURL = request.getRequestURL().toString();
            UserAgentUtil userAgentUtil = new UserAgentUtil(request);
            //IP地址
            String ipAddr = userAgentUtil.getIpAddr();

            //浏览器名称
            Browser browser = userAgentUtil.getBrowser();
            if (null != browser) {
                String browserName = browser.getName();
            }

            //浏览器版本
            Version version = userAgentUtil.getBrowserVersion();
            if (null != version) {
                String browserVersion = version.toString();
            }

            //设备名称
            String device = userAgentUtil.getDevice();
            //请求参数
            String params = JSON.toJSONString(joinPoint.getArgs(), SerializerFeature.WriteNullStringAsEmpty);
            logger.debug("开始记录系统日志");
        } catch (Exception exception) {
            logger.error(exception.getMessage());
        }
    }

    /**
     * 请求后操作
     *
     * @param ret
     */
    @AfterReturning(returning = "ret", pointcut = "actionLogAspect()")
    public void doAfterReturning(Object ret) {

    }

    /**
     * 异常通知
     *
     * @param e
     */
    @AfterThrowing(pointcut = "actionLogAspect()", throwing = "e")
    public void doAfterThrowable(Throwable e) {

    }
}
