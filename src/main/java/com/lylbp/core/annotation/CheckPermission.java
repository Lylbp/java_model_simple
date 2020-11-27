package com.lylbp.core.annotation;

import java.lang.annotation.*;


/**
 * 检查权限注解
 *
 * @author weiwenbin
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckPermission {
    /**
     * 描述
     *
     * @return String
     */
    String description() default "";
}
