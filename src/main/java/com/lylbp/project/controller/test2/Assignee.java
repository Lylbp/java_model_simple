package com.lylbp.project.controller.test2;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;


/**
 * @author qiaoxing
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Assignee")
public class Assignee implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "分配人类型 1指定人员 2所属部门负责人 3主部门负责人 4发起人自己 5直属上级 6连续多级主管-不超过发起人 7连续多级主管-组织架构 8角色", required = true)
    @NotBlank
    private String assigneeType;

    @ApiModelProperty(value = "分配人 固定人员时必传")
    @Size(min = 1)
    private List<String> assigneeList;

    @ApiModelProperty(value = "审批人为空时 1自动通过 2自动转交给管理员", required = true)
    @NotNull
    private Integer assigneeIsEmpty;

    @ApiModelProperty(value = "最高负责人等级assigneeType为5、6、7必传， 1代表直属上级、1级主部门负责人、最该级部门负责人")
    private Integer lastLeaderLevel;

    @ApiModelProperty(value = "角色集合assigneeType为8必传")
    private List<String> roleList;

    //    @ApiModelProperty(value = "审批人与发起人为同一人时 1自动跳过 2由发起人对自己审批 3转交给所属部门负责人审批 4转交给主部门负责人审批")
//    private Integer assigneeIsStarter;

    @ApiModelProperty(value = "审批方式 1 依次审批（按顺序同意或拒绝）2会签（需要所有审批人都同意才可通过）" +
            "3或签（其中一名审批人同意或拒绝即可）")
    private String multiMode;

    @ApiModelProperty(value = "依次审批或会签 完成条件表达式 默认${nrOfInstances == nrOfCompletedInstances} " +
            "nrOfInstances:实例总数" +
            "nrOfActiveInstances:当前还未完成的实例个数,对于顺序的多实例,此值总是1" +
            "nrOfCompletedInstances:已完成的实例个数 ")
    private String completionConditionExpression = "${nrOfInstances == nrOfCompletedInstances}";
}