package com.lylbp.project.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.lylbp.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统定时任务表
 * </p>
 *
 * @author weiwenbin
 * @since 2020-10-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_s_task")
@ApiModel(value = "Task对象", description = "系统定时任务表 ")
public class Task extends BaseEntity<Task> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务id")
    @TableId(value = "task_id", type = IdType.ASSIGN_UUID)
    private String taskId;

    @ApiModelProperty(value = "任务名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "任务描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "任务备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "执行时间cron表达式")
    @TableField("execution_time")
    private String executionTime;

    @ApiModelProperty(value = "执行类名称")
    @TableField("execution_class_name")
    private String executionClassName;

    @ApiModelProperty(value = "任务状态(0: 暂停 1:启动)")
    @TableField("status")
    private Integer status;

    @Override
    protected Serializable pkVal() {
        return this.taskId;
    }

}
