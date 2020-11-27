package com.lylbp.common.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 标签实体
 *
 * @Author weiwenbin
 * @Date 2020-03-24 16:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class LabelVO {
    @ApiModelProperty(value = "标签码", required = true)
    private String code;

    @ApiModelProperty(value = "标签值", required = true)
    private String value;
}
