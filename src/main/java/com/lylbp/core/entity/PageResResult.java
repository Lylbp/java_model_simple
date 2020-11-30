package com.lylbp.core.entity;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * 分页结果集
 *
 * @author weiwenbin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel
public class PageResResult<T> {
    @ApiModelProperty(value = "当页数据")
    private List<T> records;
    @ApiModelProperty(value = "当前页")
    private Long pageNum;
    @ApiModelProperty(value = "每页显示数")
    private Long pageSize;
    @ApiModelProperty(value = "总数据量")
    private Long totalCount;

    public PageResResult(Page<T> page) {
        this.setRecords(page.getRecords());
        this.setPageSize(page.getSize());
        this.setPageNum(page.getCurrent());
        this.setTotalCount(page.getTotal());
    }

    public PageResResult(DataPage<T> dataPage) {
        this.setRecords(dataPage.getRecords());
        this.setPageSize(dataPage.getSize());
        this.setPageNum(dataPage.getCurrent());
        this.setTotalCount(dataPage.getTotal());
    }
}
