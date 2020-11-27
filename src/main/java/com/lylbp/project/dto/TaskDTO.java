package com.lylbp.project.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 系统定时任务表 DTO类
 * </p>
 *
 * @author weiwenbin
 * @since 2020-10-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "TaskDTO", description = "注意！！当该对象作为body传参时，对象名称并不是key,直接将对象值json字符串放入body实体即可！！")
public class TaskDTO {
    @ApiModelProperty(value = "任务id 添加时传空串")
    @Length(max = 32)
    private String taskId;

    @ApiModelProperty(value = "任务名称")
    @Length(max = 64)
    @NotBlank
    private String name;

    @ApiModelProperty(value = "任务描述")
    @Length(max = 128)
    @NotBlank
    private String description;

    @ApiModelProperty(value = "任务备注")
    @Length(max = 128)
    private String remark;

    @ApiModelProperty(value = "执行时间cron表达式")
    @Length(max = 32)
    @NotBlank
    private String executionTime;

    @ApiModelProperty(value = "执行类名称")
    @Length(max = 128)
    @NotBlank
    private String executionClassName;

    @ApiModelProperty(value = "任务状态(0: 暂停 1:启动)")
    @Min(value = 0)
    @Max(value = 1)
    private Integer status;
}