package com.lylbp.common.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 返回结果集对象
 *
 * @author weiwenbin
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ResResult<T> {
    @ApiModelProperty(value = "code")
    public int code;

    @ApiModelProperty(value = "信息")
    private String msg;

    @ApiModelProperty(value = "返回数据")
    private T data;
}
