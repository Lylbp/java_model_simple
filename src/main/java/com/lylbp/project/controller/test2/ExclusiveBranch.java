package com.lylbp.project.controller.test2;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author qiaoxing
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ExclusiveBranch")
public class ExclusiveBranch implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "条件名称", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(value = "条件类型 1手写表达式 2选择表达式", required = true)
    @NotBlank
    private String conditionType;

    @ApiModelProperty(value = "手写条件")
    private String conditionExpression;

    @ApiModelProperty(value = "选择条件集合")
    @Valid
    private List<ConditionGroup> conditionGroupList;

    @ApiModelProperty(value = "分支内部流程")
    private ProcessNode exclusiveBranchNode;

    @ApiModelProperty(value = "跳转id")
    private String jumpId;
}
