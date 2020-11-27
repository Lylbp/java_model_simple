package com.lylbp.project.qo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.*;

/**
 * <p>
 * 后台管理员表QO类
 * </p>
 *
 * @author weiwenbin
 * @since 2020-07-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AdminQO对象", description = "注意！！当该对象作为body传参时，对象名称并不是key,直接将对象值json字符串放入body实体即可！！")
public class AdminQO {
    @ApiModelProperty("是否启用查询 传空串此条件不生效")
    String isEnabled;
}