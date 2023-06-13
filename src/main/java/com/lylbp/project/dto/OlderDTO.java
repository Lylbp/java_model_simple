package com.lylbp.project.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.*;

/**
 * <p>
 * DTO类
 * </p>
 *
 * @author weiwenbin
 * @since 2022-10-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="OlderDTO", description = "注意！！当该对象作为body传参时，对象名称并不是key,直接将对象值json字符串放入body实体即可！！")
public class OlderDTO {
    @ApiModelProperty(value = "示列请对应修改")
    private String name;
}