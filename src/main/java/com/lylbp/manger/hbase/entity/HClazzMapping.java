package com.lylbp.manger.hbase.entity;

import com.lylbp.manger.hbase.handler.HRowHandler;
import lombok.Data;
import org.apache.hadoop.hbase.TableName;


/**
 * es分页类
 *
 * @Author weiwenbin
 * @Date: 2020/11/18 上午9:17
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
