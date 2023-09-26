package com.lylbp.project.controller.test2;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * @author: weiwenbin
 * @date: 2023/03/10 15:48
 * @description: 流程信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProcessInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "流程节点信息", required = true)
    @NotNull
    @Valid
    private ProcessNode processNode;

    @ApiModelProperty(value = "流程配置信息", required = true)
    @NotNull
    @Valid
    private ProcessSetting processSetting;

    @ApiModelProperty(value = "流程ui")
    private String processUi;
}