package com.lylbp.core.entity;

import lombok.Data;

import java.util.List;

/**
 * 数据分页类
 *
 * @author weiwenbin
 * @date 2020/11/18 上午9:17
 */
@Data
public class DataPage<T> {
    /**
     * 记录
     */
    protected List<T> records;

    /**
     * 总数
     */
    protected long total;

    /**
     * 每页显示数
     */
    protected long size;

    /**
     * 当前页
     */
    protected long current;

    private DataPage() {
    }

    public DataPage(long current, long size) {
        this();
        if (current <= 0) {
            throw new IllegalArgumentException("参数错误");
        }

        if (size < 1) {
            throw new IllegalArgumentException("参数错误");
        }

        this.current = current;
        this.size = size;
    }
}
