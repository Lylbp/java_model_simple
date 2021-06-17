package com.lylbp.manager.hbase.demo.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.lylbp.common.entity.DataPage;
import com.lylbp.common.entity.PageResResult;
import com.lylbp.common.entity.ResResult;
import com.lylbp.common.utils.ResResultUtil;
import com.lylbp.manager.elasticsearch.demo.entity.ESTestUser;
import com.lylbp.manager.elasticsearch.demo.qo.TestUserQO;
import com.lylbp.manager.elasticsearch.demo.service.TestUserService;
import com.lylbp.manager.hbase.demo.entity.HbaseTestUser;
import com.lylbp.manager.hbase.handler.exception.HandlerException;
import com.lylbp.manager.hbase.handler.exception.HbaseAnnotationException;
import com.lylbp.manager.hbase.service.HBaseBeanService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * HbaseTestController
 *
 * @author weiwenbin
 */
@Slf4j
@RequestMapping("/test/hbase")
@RestController
@ConditionalOnProperty(prefix = "hbase", name = "enabled", havingValue = "true")
public class HbaseTestController {
    @Resource
    private HBaseBeanService<HbaseTestUser> hbaseService;

    @Resource
    private TestUserService testUserService;

    private final static String TABLE_NAME = "test_user";

    @GetMapping("/createTable")
    @ApiOperation("创建表")
    public ResResult<Boolean> createTable() throws Exception {
        ArrayList<String> list = new ArrayList<>();
        list.add("info");

        return ResResultUtil.success(hbaseService.createTable(hbaseService.getTableName(TABLE_NAME), list));
    }

    @GetMapping("/createTableByEntity")
    @ApiOperation("通过实体注解创建表")
    public ResResult<Boolean> createTableByEntity() throws Exception {
        return ResResultUtil.success(hbaseService.createTable(HbaseTestUser.class));
    }


//    @GetMapping("/dropTable")
//    @ApiOperation("删除表")
//    public ResResult<Boolean> dropTable() throws Exception {
//        return ResResultUtil.success(hbaseService.dropTable(hbaseService.getTableName(TABLE_NAME)));
//    }

    @PostMapping("/toPageBean")
    @ApiOperation("es与hbase配合查询分页数据")
    public ResResult<PageResResult<HbaseTestUser>> toPageBean(@RequestBody TestUserQO qo,
                                                              @RequestParam(defaultValue = "1") Integer current,
                                                              @RequestParam(defaultValue = "10") Integer size)
            throws Throwable {
        //获取符合条件的es数据并获取rowLey集合
        DataPage<ESTestUser> dataPage = new DataPage<>(current, size);
        List<ESTestUser> list = testUserService.selectSearchHitsByScroll(BeanUtil.beanToMap(qo), dataPage);
        List<String> rowKeyList = list.stream().map(ESTestUser::getRowKey).collect(Collectors.toList());

        //通过rowKey获取hbase数据
        List<HbaseTestUser> hList = hbaseService.getByRowKeyList(HbaseTestUser.class, rowKeyList);
        DataPage<HbaseTestUser> hbaseDataPage = new DataPage<>(current, size);
        hbaseDataPage.setRecords(hList);

        return ResResultUtil.makePageRsp(hbaseDataPage);
    }

    @GetMapping("/toPut")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation("插入数据")
    public ResResult<Boolean> toPut() throws IOException, HandlerException, HbaseAnnotationException, InstantiationException {
        List<HbaseTestUser> hbaseTestUserList = new ArrayList<>();
        List<ESTestUser> esTestUserList = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            ESTestUser esTestUser = new ESTestUser();
            HbaseTestUser hbaseTestUser = new HbaseTestUser();

            esTestUser.setRowKey(String.valueOf(i));
            esTestUser.setName("张三" + i);
            esTestUser.setPhone(i + "");
            esTestUser.setEmail(i + "@qq.com");
            esTestUser.setCreateTime(DateUtil.date());
            esTestUserList.add(esTestUser);

            hbaseTestUserList.add(hbaseTestUser);
        }

        hbaseService.eBatchPutData(hbaseTestUserList);

        testUserService.saveAll(esTestUserList);

        return ResResultUtil.success(true);
    }

    @GetMapping("/delete")
    @ApiOperation("删除数据")
    public ResResult<Boolean> delete() throws Throwable {
        List<HbaseTestUser> data = hbaseService.eGetPageData(HbaseTestUser.class, null, null);
        List<String> rowKeyList = data.stream().map(HbaseTestUser::getRowKey).collect(Collectors.toList());
        hbaseService.mBatchDeleteByRowKey(hbaseService.getTableName(TABLE_NAME), rowKeyList);
        return ResResultUtil.success(true);
    }
}
