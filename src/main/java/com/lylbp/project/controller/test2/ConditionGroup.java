package com.lylbp.project.controller.test2;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author weiwenbin
 * @Date 2023/3/13 上午10:16
 */
@Data
@ApiModel
public class ConditionGroup implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "条件集合")
    @Size(min = 1)
    @NotNull
    private List<ConditionInfo> conditionInfoList;
}
