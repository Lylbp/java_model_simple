package com.lylbp.project.controller.test2;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Description
 * @Author weiwenbin
 * @Date 2023/3/10 下午4:03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProcessSetting implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "1拒绝后结束 2拒绝后继续执行", required = true)
    @NotBlank
    private String processModel;

    @ApiModelProperty(value = "同一审批人在流程中多次出现时，自动去重 为true时默认为 在流程中出现多次时，仅保留第一个")
    private Boolean duplicateRemoval = Boolean.FALSE;

    @ApiModelProperty(value = "仅在连续出现时，自动去重")
    private Boolean continuousFirst = Boolean.FALSE;

    @ApiModelProperty(value = "审批人和发起人是同一个人，审批自动通过")
    private Boolean autoExecuteOriginatorTasks = Boolean.FALSE;
}
