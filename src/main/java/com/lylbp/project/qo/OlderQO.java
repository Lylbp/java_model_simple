package com.lylbp.project.qo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.*;

/**
 * <p>
 * QO类
 * </p>
 *
 * @author weiwenbin
 * @since 2022-10-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="OlderQO对象", description = "注意！！当该对象作为body传参时，对象名称并不是key,直接将对象值json字符串放入body实体即可！！")
public class OlderQO {
    @ApiModelProperty(value = "示列请对应修改")
    private String example;
}