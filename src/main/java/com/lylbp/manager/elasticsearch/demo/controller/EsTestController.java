package com.lylbp.manager.elasticsearch.demo.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.lylbp.common.entity.DataPage;
import com.lylbp.common.entity.PageResResult;
import com.lylbp.common.entity.ResResult;
import com.lylbp.common.utils.ResResultUtil;
import com.lylbp.manager.elasticsearch.demo.dto.TestUserDTO;
import com.lylbp.manager.elasticsearch.demo.entity.ESTestUser;
import com.lylbp.manager.elasticsearch.demo.qo.TestUserQO;
import com.lylbp.manager.elasticsearch.demo.service.TestUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * es测试
 *
 * @author weiwenbin
 */
@Slf4j
@RequestMapping("/test/es")
@RestController
public class EsTestController {
    @Autowired
    private TestUserService service;

    @PostMapping("/batchSave")
    @ApiOperation("批量保存1w1数据")
    public ResResult<Boolean> batchSave() {
        List<ESTestUser> list = new ArrayList<>();
        int i = 1;
        while (i<=11000){
            ESTestUser entity = new ESTestUser();
            entity.setRowKey(String.valueOf(i));
            entity.setPhone(String.valueOf(i));
            entity.setName(String.valueOf(i));
            entity.setCreateTime(DateUtil.date().offset(DateField.MINUTE, i));
            i++;
            list.add(entity);
        }

        service.saveAll(list);
        return ResResultUtil.success(true);
    }

    @PostMapping("/save")
    @ApiOperation("保存")
    public ResResult<Boolean> save(@RequestBody @Valid TestUserDTO dto) {
        ESTestUser entity = new ESTestUser();
        BeanUtil.copyProperties(dto, entity);
        entity.setCreateTime(DateUtil.date());
        service.save(entity);

        return ResResultUtil.success(true);
    }

    @PostMapping("/findAllSort")
    @ApiOperation("查询所有(超过1w会报错，除非配置max_result_window)[排序]")
    public ResResult<List<ESTestUser>> findAllSort() {
        Sort sort = Sort.sort(ESTestUser.class).by(ESTestUser::getCreateTime).descending();
        return ResResultUtil.success(service.findAll(sort));
    }


    @PostMapping("/findAll")
    @ApiOperation("查询所有(超过1w会报错，除非配置max_result_window)")
    public ResResult<List<ESTestUser>> findAll() {
        return ResResultUtil.success(service.findAll());
    }

    @PostMapping("/selectSearchHitsByScroll")
    @ApiOperation("深度分页查询所有")
    public ResResult<List<ESTestUser>> selectSearchHitsByScroll(@RequestBody TestUserQO qo) {
        List<ESTestUser> list = service.selectSearchHitsByScroll(BeanUtil.beanToMap(qo), null);
        return ResResultUtil.success(list);
    }

    @PostMapping("/selectSearchHitsByScrollPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页 默认1", defaultValue = "1", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页显示数 默认10", defaultValue = "10", dataType = "Integer", paramType = "query")
    })
    @ApiOperation("深度分页")
    public ResResult<PageResResult<ESTestUser>> selectSearchHitsByScrollPage(@RequestBody TestUserQO qo, @RequestParam(defaultValue = "1") Integer current,
                                                                             @RequestParam(defaultValue = "10") Integer size) {
        DataPage<ESTestUser> dataPage = new DataPage<>(current, size);
        service.selectSearchHitsByScroll(BeanUtil.beanToMap(qo), dataPage);

        return ResResultUtil.makePageRsp(dataPage);
    }


    @PostMapping("/selectSearchHitsByScrollAndFrom")
    @ApiOperation("潜度分页查询所有")
    public ResResult<List<ESTestUser>> selectSearchHitsByFrom(@RequestBody TestUserQO qo) {
        List<ESTestUser> list = service.selectSearchHitsByFrom(BeanUtil.beanToMap(qo), null);
        return ResResultUtil.success(list);
    }

    @PostMapping("/selectSearchHitsByScrollAndFromPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页 默认1", defaultValue = "1", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页显示数 默认10", defaultValue = "10", dataType = "Integer", paramType = "query")
    })
    @ApiOperation("潜度分页")
    public ResResult<PageResResult<ESTestUser>> selectSearchHitsByFrom(@RequestBody TestUserQO qo,
                                                                                    @RequestParam(defaultValue = "1") Integer current,
                                                                                    @RequestParam(defaultValue = "10") Integer size) {
        DataPage<ESTestUser> dataPage = new DataPage<>(current, size);
        service.selectSearchHitsByFrom(BeanUtil.beanToMap(qo), dataPage);

        return ResResultUtil.makePageRsp(dataPage);
    }


    @PostMapping("/findByPhone")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号码", required = true, dataType = "String", paramType = "form"),
    })
    @ApiOperation("通过手机号查询[不写查询语句]")
    public ResResult<ESTestUser> findByPhone(@NotBlank String phone) {
        ESTestUser byPhone = service.findByPhone(phone);
        return ResResultUtil.success(byPhone);
    }

    @PostMapping("/selectByName")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", required = true, dataType = "String", paramType = "form"),
    })
    @ApiOperation("通过名称查询[手写查询语句]")
    public ResResult<ESTestUser> selectByName(@NotBlank String name) {
        return ResResultUtil.success(service.selectByName(name));
    }


    @PostMapping("/deleteAll")
    @ApiOperation("删除所有")
    public ResResult<Boolean> deleteAll() {
        service.deleteAll();
        return ResResultUtil.success(true);
    }

}

