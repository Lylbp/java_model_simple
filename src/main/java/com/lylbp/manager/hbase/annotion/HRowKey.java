package com.lylbp.manager.hbase.annotion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解: HRowkey
 * 字段的注解,配置代表该字段是hbase的rowKey
 * 必写,不能与HColumn、HIgnore共用
 *
 * @author weiwenbin
 * @date 2020-11-01 12:09
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HRowKey {
}
