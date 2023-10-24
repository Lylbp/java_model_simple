package com.lylbp.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author weiwenbin
 * @Date 2023/10/23 下午5:10
 */
@Data
@ApiModel
public class PageQueryDTO {
    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页")
    private Long current;

    /**
     * 每页的数量
     */
    @ApiModelProperty(value = "每页的数量")
    private Long size;

    /**
     * 正序排字段集合
     */
    @ApiModelProperty(value = "正序排字段集合")
    private List<String> ascColumnList;

    /**
     * 倒序排字段集合
     */
    @ApiModelProperty(value = "倒序排字段集合")
    private List<String> descColumnList;
}
