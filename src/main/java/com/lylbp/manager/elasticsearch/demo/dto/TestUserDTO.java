package com.lylbp.manager.elasticsearch.demo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author weiwenbin
 * @date 2020/11/18 上午11:15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestUserDTO {
    @ApiModelProperty(value = "rowKey", required = true)
    @NotBlank
    @Length(max = 32)
    private String rowKey;

    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank
    @Length(max = 32)
    private String phone;

    @ApiModelProperty(value = "名称", required = true)
    @NotBlank
    @Length(max = 32)
    private String name;
}
