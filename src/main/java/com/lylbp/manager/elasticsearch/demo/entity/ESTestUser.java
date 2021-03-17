package com.lylbp.manager.elasticsearch.demo.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author weiwenbin
 * @date 2020/11/13 上午9:30
 */
@Data
@Document(indexName = "test_user")
public class ESTestUser {
    @Field(type = FieldType.Keyword)
    @Id
    @ApiModelProperty(value = "rowKey")
    private String rowKey;

    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "手机号")
    private String phone;

    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "email")
    @Transient
    private String email;

    @Field(type = FieldType.Keyword, format = DateFormat.basic_date_time)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
