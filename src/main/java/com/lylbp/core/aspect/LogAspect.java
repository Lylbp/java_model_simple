package com.lylbp.core.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lylbp.common.utils.UserAgentUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;


@Aspect
@Component
@Slf4j
public class LogAspect {
    /**
     * 开始时间
     */
    private long start;

    /**
     * 结束时间
     */
    private long end;

    @Pointcut("execution(public * com.lylbp.project.controller.*.*.*(..))")
    public void log() {
    }

    /**
     * 执行前操作
     *
     * @param joinPoint
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("日志前置通知");
        start = System.currentTimeMillis();

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == attributes) {
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        UserAgentUtil userAgentUtil = new UserAgentUtil(request);

        // 记录下请求内容
        log.info("请求URL ： {}", request.getRequestURL());
        log.info("请求IP  ： {}", userAgentUtil.getIpAddr());
        log.info("请求方法 ： {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("请求参数 ： {}", JSON.toJSONString(joinPoint.getArgs(), SerializerFeature.WriteNullStringAsEmpty));
    }

    /**
     * 执行后操作
     *
     * @param object
     */
    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object) {
        System.out.println("日志结束返回通知");
        end = System.currentTimeMillis();
        log.info("执行时间  ：{}ms", (end - start));
        log.info("返回 : " + JSON.toJSONString(object));

    }
}