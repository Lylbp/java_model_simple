package com.lylbp.project.controller.test2;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * ProcessCustom添加表单对象
 * </p>
 *
 * @Description ProcessCustom添加表单对象
 * @Author weiwenbin
 * @Date 2023-02-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ProcessCustomAddForm")
public class ProcessCustomAddForm implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "流程名称", required = true)
    private String processName;

    @ApiModelProperty(value = "流程信息对象", required = true)
    @NotNull
    @Valid
    private ProcessInfo processInfo;

    @ApiModelProperty(value = "流程类型", required = true)
    @NotBlank
    private String processType;

    @ApiModelProperty(value = "流程子类型", required = true)
    @NotBlank
    private String processSubType;

    @ApiModelProperty(value = "1启用 2禁用", required = true)
    @NotBlank
    private String processStatus;

}