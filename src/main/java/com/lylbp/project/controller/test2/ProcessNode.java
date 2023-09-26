package com.lylbp.project.controller.test2;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;


/**
 * @author qiaoxing
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ProcessNode")
public class ProcessNode implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "节点id 不为分支任务必需传")
    @Pattern(regexp = "^([a-zA-Z]+)([a-zA-Z0-9-_]*)$")
    private String id;

    @ApiModelProperty(value = "节点名称 不为分支任务必需传")
    private String name;

    @ApiModelProperty(value = "节点类型 1发起任务 2审核任务 3分支任务", required = true)
    @NotNull
    private Integer nodeType;

    @ApiModelProperty(value = "下一节点")
    @Valid
    private ProcessNode next;

    @ApiModelProperty(value = "分支 分支任务必传")
    @Valid
    private List<ExclusiveBranch> exclusive;

    @ApiModelProperty(value = "跳转")
    @Valid
    private List<ExclusiveBranch> jump;

    @ApiModelProperty(value = "委托人 2审核任务必传")
    @Valid
    private Assignee assignee;

    @ApiModelProperty(value = "完成任务是否发送mq 0:否 1:是 节点类型不为分支任务必需传")
    private Integer sendMessage;
}