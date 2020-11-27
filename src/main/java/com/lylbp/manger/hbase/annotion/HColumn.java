package com.lylbp.manger.hbase.annotion;

import java.lang.annotation.*;

/**
 * 自定义注解:列与列簇
 * 配置该注解代表当前字段是一个非rowKey的普通列
 * 理论上必写不然没意义,不能与HIgnore、HRowKey共用
 *
 * @Author weiwenbin
 * @Date 2020-11-01 12:09
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface HColumn {
    /**
     * 列簇
     * 该值可以不写,当是不写时必须在HTable中填写defaultFamily
     *
     * @return String
     */
    String family() default "";

    /**
     * 族
     * 该值可以不写若不写默认与字段名一致
     *
     * @return
     */
    String qualifier() default "";

    /**
     * 时间戳
     *
     * @return
     */
    boolean timestamp() default false;
}

