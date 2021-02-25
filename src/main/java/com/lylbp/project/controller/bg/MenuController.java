package com.lylbp.project.controller.bg;

import cn.hutool.core.util.ObjectUtil;
import com.lylbp.common.annotation.CheckPermission;
import com.lylbp.common.enums.ResResultEnum;
import com.lylbp.common.entity.PageResResult;
import com.lylbp.project.qo.MenuQO;
import com.lylbp.project.service.*;
import com.lylbp.project.vo.MenuNodeVO;
import com.lylbp.project.vo.MenuVO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.hutool.core.bean.BeanUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lylbp.common.entity.ResResult;
import com.lylbp.common.utils.ResResultUtil;
import com.lylbp.project.entity.Menu;
import com.lylbp.project.dto.MenuDTO;

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
 * 权限管理-菜单表  前端控制器
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
@RestController
@RequestMapping("/bg/menu")
@Api(tags = "菜单相关")
public class MenuController {
    @Resource
    private MenuService service;

    @PostMapping(value = "/getPageList")
    @ApiOperation("菜单-获取菜单分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页 默认1", defaultValue = "1", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页显示数 默认10", defaultValue = "10", dataType = "Integer", paramType = "query")
    })
    @CheckPermission(description = "菜单-获取菜单分页列表")
    public ResResult<PageResResult<MenuVO>> getList(@RequestBody MenuQO query,
                                                    @RequestParam(defaultValue = "1") Integer current,
                                                    @RequestParam(defaultValue = "10") Integer size) {
        Map<String, Object> params = BeanUtil.beanToMap(query);

        Page<MenuVO> page = new Page<>(current, size);
        List<MenuVO> list = service.getMenuVOListByParams(page, params);
        page.setRecords(list);

        return ResResultUtil.makePageRsp(page);
    }

    @PostMapping(value = "/getTree")
    @ApiOperation("菜单-获取所有菜单的树")
    public ResResult<List<MenuNodeVO>> getTree() {
        List<MenuVO> list = service.getMenuVOListByParams(null, new HashMap<>(1));
        return ResResultUtil.success(service.recursionHandleMenuVOList(list, "0"));
    }

    @GetMapping(value = "/info/{id}")
    @ApiOperation("菜单-根据菜单id获取菜单详情")
    @CheckPermission(description = "菜单-根据菜单id获取菜单详情")
    public ResResult<MenuVO> getInfoById(@PathVariable(value = "id") @Validated @NotBlank String id) {
        MenuVO entity = service.getOneMenuVOBy("id", id);

        return ResResultUtil.success(entity);
    }

    @PostMapping(value = "/add")
    @ApiOperation("菜单-添加菜单")
    @Transactional(rollbackFor = Exception.class)
    @CheckPermission(description = "菜单-添加菜单")
    public ResResult<Boolean> add(@RequestBody @Validated MenuDTO dto) {
        Menu entity = new Menu();
        BeanUtil.copyProperties(dto, entity);
        entity.setMenuId("");

        return ResResultUtil.success(service.edit(entity));
    }

    @PostMapping(value = "/edit")
    @ApiOperation("菜单-编辑菜单")
    @Transactional(rollbackFor = Exception.class)
    @CheckPermission(description = "菜单-编辑菜单")
    public ResResult<Boolean> edit(@RequestBody @Validated MenuDTO dto) {
        Menu entity = new Menu();
        BeanUtil.copyProperties(dto, entity);
        if (ObjectUtil.isEmpty(dto.getMenuId())) {
            return ResResultUtil.error(ResResultEnum.PARAM_ERROR);
        }

        return ResResultUtil.success(service.edit(entity));
    }

    @PostMapping(value = "/batchDeleteByIds")
    @ApiOperation("菜单-根据菜单id批量删除菜单")
    @CheckPermission(description = "菜单-编辑菜单")
    public ResResult<Boolean> batchDeleteByUserIds(@RequestBody @Validated @NotNull List<String> idList) {
        return ResResultUtil.success(service.removeByIds(idList));
    }
}

