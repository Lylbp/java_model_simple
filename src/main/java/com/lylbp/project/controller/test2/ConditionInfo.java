package com.lylbp.project.controller.test2;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author weiwenbin
 * @Date 2023/3/13 上午10:16
 */
@Data
@ApiModel
public class ConditionInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "参数唯一标识")
    private String paramKey;
    @ApiModelProperty(value = "参数值")
    private List<Object> value;
    @ApiModelProperty(value = "参数类型 string number")
    private String valueType;
    @ApiModelProperty(value = "比较类型 =、>、<、>=、<=、in")
    private String compareType;
    @ApiModelProperty(value = "参数是否在比较类型后面 只有数值类型有效 （true：days>=2 false：2>=days 默认为true）")
    private Boolean numberAfterCompareType = true;
}
