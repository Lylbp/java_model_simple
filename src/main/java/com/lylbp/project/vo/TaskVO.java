package com.lylbp.project.vo;

import com.lylbp.project.entity.Task;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.*;

/**
 * <p>
 * 系统定时任务表 VO类
 * </p>
 *
 * @author weiwenbin
 * @since 2020-10-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TaskVO对象")
public class TaskVO extends Task {
}