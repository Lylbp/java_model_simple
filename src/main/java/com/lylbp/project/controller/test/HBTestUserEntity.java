package com.lylbp.project.controller.test;

import com.lylbp.manager.hbase.annotion.HColumn;
import com.lylbp.manager.hbase.annotion.HTable;
import com.lylbp.manager.hbase.annotion.HRowKey;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author weiwenbin
 * @date 2020/11/3 下午2:18
 */
@HTable(tableName = "test_user")
@Data
public class HBTestUserEntity {
    @HRowKey
    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "rowKey")
    private String rowKey;

    @HColumn(family = "info", qualifier = "phone")
    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "手机号")
    private String phone;

    @HColumn(family = "info", qualifier = "name")
    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "名称")
    private String name;

    @HColumn(family = "info", qualifier = "createTime")
    @Field(type = FieldType.Keyword, format = DateFormat.basic_date_time)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "非es字段测试")
    @Transient
    private String noFiled = "123";
}
