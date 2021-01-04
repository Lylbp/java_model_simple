package com.lylbp.manager.hbase.annotion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解: HTable
 * hbase表注解
 * 必写
 *
 * @author weiwenbin
 * @date 2020-11-01 12:09
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HTable {
    /**
     * 默认列簇
     * 当HColumn注解中未明确时以她为准
     *
     * @return String
     */
    String defaultFamily() default "";

    /**
     * 表名称
     *
     * @return String
     */
    String tableName() default "";
}
