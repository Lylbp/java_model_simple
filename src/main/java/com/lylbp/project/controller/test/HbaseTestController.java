package com.lylbp.project.controller.test;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.lylbp.core.entity.PageResResult;
import com.lylbp.core.entity.ResResult;
import com.lylbp.common.utils.ResResultUtil;
import com.lylbp.core.entity.DataPage;
import com.lylbp.manger.elasticsearch.demo.entity.ESTestUser;
import com.lylbp.manger.elasticsearch.demo.qo.TestUserQO;
import com.lylbp.manger.elasticsearch.demo.service.TestUserService;
import com.lylbp.manger.hbase.handler.exception.HandlerException;
import com.lylbp.manger.hbase.handler.exception.HbaseAnnotationException;
import com.lylbp.manger.hbase.service.HBaseBeanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author weiwenbin
 */
@Slf4j
@Controller
@RequestMapping("/test/hbase")
public class HbaseTestController {
    @Resource
    private HBaseBeanService<ESTestUser> hbaseService;

    @Resource
    private TestUserService testUserService;

    private final static String tableName = "test_user";

    @GetMapping("/createTable")
    @ResponseBody
    public ResResult<Boolean> createTable() throws Exception {
        ArrayList<String> list = new ArrayList<>();
        list.add("info");

        return ResResultUtil.success(hbaseService.createTable(hbaseService.getTableName(tableName), list));
    }

    @GetMapping("/createTableByEntity")
    @ResponseBody
    public ResResult<Boolean> createTableByEntity() throws Exception {
        return ResResultUtil.success(hbaseService.createTable(ESTestUser.class));
    }

//
//    @GetMapping("/dropTable")
//    @ResponseBody
//    public ResResult<Boolean> dropTable() throws Exception {
//        return ResResultUtil.success(hbaseService.dropTable(tableName));
//    }

    @PostMapping("/toPageBean")
    @ResponseBody
    public ResResult<PageResResult<ESTestUser>> toPageBean(@RequestBody TestUserQO qo,
                                                           @RequestParam(defaultValue = "1") Integer current,
                                                           @RequestParam(defaultValue = "10") Integer size)
            throws Throwable {
        DataPage<ESTestUser> dataPage = new DataPage<>(current, size);
        List<ESTestUser> list = testUserService.selectSearchHitsByScroll(BeanUtil.beanToMap(qo), dataPage);

        List<String> rowKeyList = list.stream().map(ESTestUser::getRowKey).collect(Collectors.toList());
        List<ESTestUser> hList = hbaseService.getByRowKeyList(ESTestUser.class, rowKeyList);

        dataPage.setRecords(hList);
        return ResResultUtil.makePageRsp(dataPage);
    }

    @GetMapping("/toPut")
    @ResponseBody
    @Transactional
    public ResResult<Boolean> toPut() throws IOException, HandlerException, HbaseAnnotationException, InstantiationException {
        List<ESTestUser> list = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            ESTestUser esTestUser = new ESTestUser();
            esTestUser.setRowKey(IdUtil.simpleUUID() + "_" + i);
            esTestUser.setName("张三" + i);
            esTestUser.setPhone(i + "");
            esTestUser.setEmail(i + "@qq.com");
            esTestUser.setCreateTime(DateUtil.date());
            list.add(esTestUser);
        }

        hbaseService.eBatchPutData(list);

        testUserService.saveAll(list);

        return ResResultUtil.success(true);
    }

    @GetMapping("/delete")
    @ResponseBody
    public ResResult<Boolean> delete() throws Throwable {
        List<ESTestUser> data = hbaseService.eGetPageData(ESTestUser.class, null, null);
        List<String> rowKeyList = data.stream().map(ESTestUser::getRowKey).collect(Collectors.toList());
        hbaseService.mBatchDeleteByRowKey(hbaseService.getTableName(tableName), rowKeyList);
        return ResResultUtil.success(true);
    }
}
