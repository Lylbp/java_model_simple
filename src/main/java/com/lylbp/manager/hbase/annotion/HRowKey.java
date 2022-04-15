package com.lylbp.manager.hbase.annotion;

import java.lang.annotation.*;

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
