package com.lylbp.project.controller.bg;

import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.hutool.core.bean.BeanUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lylbp.common.utils.ResResultUtil;
import com.lylbp.common.entity.PageResResult;
import com.lylbp.common.entity.ResResult;
import com.lylbp.project.service.OlderService;
import com.lylbp.project.entity.Older;
import com.lylbp.project.vo.OlderVO;
import com.lylbp.project.qo.OlderQO;
import com.lylbp.project.dto.OlderDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.annotation.Resource;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author weiwenbin
 * @since 2022-10-31
 */
@RestController
@RequestMapping("/older")
@Api(tags = "")
public class OlderController {
    private static Set set = new HashSet();

    @Resource
    private OlderService  service;

     /**
     * 获取数据分页列表
     *
     * @author weiwenbin
     * @date   2022-10-31
     * @return ResResult<PageResResult<OlderVO>>
     */
    @PostMapping(value = "/getList")
    @ApiOperation("获取分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页 默认1", defaultValue = "1", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页显示数 默认10", defaultValue = "10", dataType = "Integer", paramType = "query")
    })
    public ResResult<PageResResult<OlderVO>> getList(@RequestBody OlderQO query,
    @RequestParam(defaultValue = "1") Integer current,@RequestParam(defaultValue = "10") Integer size) {
            Map<String, Object> params = BeanUtil.beanToMap(query);

            Page<OlderVO> page = new Page<>(current, size);
            List<OlderVO> list = service.getOlderVOListByParams(page, params);
            page.setRecords(list);

            return ResResultUtil.makePageRsp(page);
    }

    /**
     * 获取信息
     *
     * @author weiwenbin
     * @date 2022-10-31
     * @return ResResult<OlderVO>
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取信息")
    public ResResult<OlderVO> getInfoById(@PathVariable(value = "id") @NotBlank String id) {
        OlderVO entity = service.getOneOlderVOBy("id", id);

        return ResResultUtil.success(entity);
    }


    /**
    * 添加
    *
    * @author weiwenbin
    * @date   2022-10-31
    * @return ResResult<OlderVO>
    */
    @PostMapping(value = "/add")
    @ApiOperation("添加")
    public ResResult<Boolean> add(@RequestBody @Validated OlderDTO dto) {
//        Runnable runnable = () -> {
//            Older entity = new Older();
//            entity.setName("456789");
//            try {
//                service.edit(entity);
//            }catch (Exception exception){
//                System.out.println("重复啦" + entity.getId());
//                set.add(entity.getId());
//            }
//        };
//        for (int i = 0; i < 10000; i++) {
//            new Thread(runnable).start();
//        }
//        return ResResultUtil.success(true);
        Older entity = new Older();
        BeanUtil.copyProperties(dto, entity);

        return ResResultUtil.success(service.edit(entity));
    }

    /**
    * 编辑
    *
    * @author weiwenbin
    * @date   2022-10-31
    * @return ResResult<Boolean>
    */
    @PutMapping(value = "/edit")
    @ApiOperation("编辑")
    public ResResult<Boolean> edit(@RequestBody @Validated OlderDTO dto) {
        Older entity = new Older();
        BeanUtil.copyProperties(dto, entity);

        return ResResultUtil.success(service.edit(entity));
    }

    /**
     * 通过主键批量删除
     *
     * @author weiwenbin
     * @date   2022-10-31
     * @return ResResult<Boolean>
     */
    @DeleteMapping(value = "/batchDelete")
    @ApiOperation("通过主键批量删除")
    public ResResult<Boolean> batchDelete(@RequestBody @NotNull List<String> idList) {
        return ResResultUtil.success(service.removeByIds(idList));
    }
}

