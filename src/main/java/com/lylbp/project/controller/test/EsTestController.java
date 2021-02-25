package com.lylbp.project.controller.test;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.lylbp.common.entity.PageResResult;
import com.lylbp.common.entity.ResResult;
import com.lylbp.common.utils.ResResultUtil;
import com.lylbp.common.entity.DataPage;
import com.lylbp.manager.elasticsearch.demo.dto.TestUserDTO;
import com.lylbp.manager.elasticsearch.demo.entity.ESTestUser;
import com.lylbp.manager.elasticsearch.demo.qo.TestUserQO;
import com.lylbp.manager.elasticsearch.demo.service.TestUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author weiwenbin
 */
@Slf4j
@RequestMapping("/test/es")
@RestController
public class EsTestController {
    @Autowired
    private TestUserService service;

    @PostMapping("/save")
    public ResResult<Boolean> save(@RequestBody @Valid TestUserDTO dto) {
        ESTestUser entity = new ESTestUser();
        BeanUtil.copyProperties(dto, entity);
        entity.setCreateTime(DateUtil.date());
        service.save(entity);

        return ResResultUtil.success(true);
    }

    @PostMapping("/findAllSort")
    public ResResult<List<ESTestUser>> findAllSort() {
        Sort sort = Sort.sort(ESTestUser.class).by(ESTestUser::getCreateTime).descending();
        return ResResultUtil.success(service.findAll(sort));
    }


    @PostMapping("/findAll")
    public ResResult<List<ESTestUser>> findAll() {
        return ResResultUtil.success(service.findAll());
    }

    @PostMapping("/selectSearchHitsByScroll")
    public ResResult<List<ESTestUser>> selectSearchHitsByScroll(@RequestBody TestUserQO qo) {
        List<ESTestUser> list = service.selectSearchHitsByScroll(BeanUtil.beanToMap(qo), null);
        return ResResultUtil.success(list);
    }

    @PostMapping("/selectSearchHitsByScrollPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页 默认1", defaultValue = "1", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页显示数 默认10", defaultValue = "10", dataType = "Integer", paramType = "query")
    })
    public ResResult<PageResResult<ESTestUser>> selectSearchHitsByScrollPage(@RequestBody TestUserQO qo, @RequestParam(defaultValue = "1") Integer current,
                                                            @RequestParam(defaultValue = "10") Integer size) {
        DataPage<ESTestUser> dataPage = new DataPage<>(current, size);
        service.selectSearchHitsByScroll(BeanUtil.beanToMap(qo), dataPage);
        
        return ResResultUtil.makePageRsp(dataPage);
    }


    @PostMapping("/selectSearchHitsByScrollAndFrom")
    public ResResult<List<ESTestUser>> selectSearchHitsByScrollAndFrom(@RequestBody TestUserQO qo) {
        List<ESTestUser> list = service.selectSearchHitsByScrollAndFrom(BeanUtil.beanToMap(qo), null);
        return ResResultUtil.success(list);
    }

    @PostMapping("/selectSearchHitsByScrollAndFromPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页 默认1", defaultValue = "1", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页显示数 默认10", defaultValue = "10", dataType = "Integer", paramType = "query")
    })
    public ResResult<PageResResult<ESTestUser>> selectSearchHitsByScrollAndFromPage(@RequestBody TestUserQO qo, @RequestParam(defaultValue = "1") Integer current,
                                                            @RequestParam(defaultValue = "10") Integer size) {
        DataPage<ESTestUser> dataPage = new DataPage<>(current, size);
        service.selectSearchHitsByScrollAndFrom(BeanUtil.beanToMap(qo), dataPage);

        return ResResultUtil.makePageRsp(dataPage);
    }


    @PostMapping("/findByPhone")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号码", required = true, dataType = "String", paramType = "form"),
    })
    public ResResult<ESTestUser> findByPhone(@NotBlank String phone) {
        ESTestUser byPhone = service.findByPhone(phone);
        return ResResultUtil.success(byPhone);
    }

    @PostMapping("/selectByName")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", required = true, dataType = "String", paramType = "form"),
    })
    public ResResult<ESTestUser> selectByName(@NotBlank String name) {
        return ResResultUtil.success(service.selectByName(name));
    }


    @PostMapping("/deleteAll")
    public ResResult<Boolean> deleteAll() {
        service.deleteAll();
        return ResResultUtil.success(true);
    }

}
