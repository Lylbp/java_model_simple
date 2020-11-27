package com.lylbp.core.annotation;

import java.lang.annotation.*;


/**
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
