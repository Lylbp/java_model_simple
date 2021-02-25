package com.lylbp.project.qo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.*;
import org.hibernate.validator.constraints.Length;

/**
 * <p>
 * 系统定时任务表 QO类
 * </p>
 *
 * @author weiwenbin
 * @since 2020-10-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "TaskQO对象", description = "注意！！当该对象作为body传参时，对象名称并不是key,直接将对象值json字符串放入body实体即可！！")
public class TaskQO {
    @ApiModelProperty(value = "任务名称模糊查询 传空串此条件不生效")
    private String nameLike;

    @ApiModelProperty(value = "任务描述模糊查询 传空串此条件不生效")
    private String descriptionLike;

    @ApiModelProperty(value = "任务备注")
    @Length(max = 128)
    private String remarkLike;
}