package com.lylbp.manager.hbase.entity;

import com.lylbp.manager.hbase.handler.HRowHandler;
import lombok.Data;
import org.apache.hadoop.hbase.TableName;


/**
 * es分页类
 *
 * @author weiwenbin
 * @date 2020/11/18 上午9:17
 */
@Data
public class HClazzMapping<T> {
    /**
     * 表名称
     */
    private TableName tableName;

    /**
     * 列字段映射
     */
    private HRowHandler<T> rowHandler;
}
