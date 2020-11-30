package com.lylbp.project.controller.test;

import com.lylbp.project.entity.Admin;
import com.lylbp.manger.elasticsearch.demo.entity.ESTestUser;
import com.lylbp.manger.hbase.converter.DefaultConversionService;
import com.lylbp.manger.hbase.handler.exception.HbaseAnnotationException;
import com.lylbp.manger.hbase.service.HBaseBeanService;
import org.apache.hadoop.hbase.TableName;

import java.io.IOException;

/**
 * @author weiwenbin
 * @date 2020/11/4 上午8:53
 */
public class Test {
    public static void main(String[] args) throws IOException, HbaseAnnotationException, InstantiationException {
        HBaseBeanService<ESTestUser> hBaseBeanService = new HBaseBeanService<>();
        hBaseBeanService.getHClazzMapping(ESTestUser.class);
    }
}
