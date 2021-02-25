package com.lylbp.manager.hbase.annotion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解:忽略
 * 配置该注解代表当前字段不存在于hbase
 * 非必须,不能于HColumn或HRowKey共用
 *
 * @author weiwenbin
 * @date 2020-11-01 12:09
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HIgnore {
}
