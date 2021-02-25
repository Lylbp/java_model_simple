package com.lylbp.common.annotation;

import java.lang.annotation.*;


/**
 * 操作日志注解
 *
 * @author weiwenbin
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ActionLog {
    /**
     * 描述
     *
     * @return String
     */
    String description() default "";
}
