package com.lylbp.project.controller.test;

import com.lylbp.manager.elasticsearch.demo.entity.ESTestUser;
import com.lylbp.manager.hbase.handler.exception.HbaseAnnotationException;
import com.lylbp.manager.hbase.service.HBaseBeanService;

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
