package com.lylbp.manager.hbase.demo.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author weiwenbin
 * @date 2020/11/17 上午9:01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "TestUserQO", description = "注意！！当该对象作为body传参时，对象名称并不是key,直接将对象值json字符串放入body实体即可！！")
public class TestUserQO {
    @ApiModelProperty("名称模糊查询 传空串此条件不生效")
    private String nameLike;

    @ApiModelProperty("创建开始时间 传空串此条件不生效")
    private String createTimeGt;
}
