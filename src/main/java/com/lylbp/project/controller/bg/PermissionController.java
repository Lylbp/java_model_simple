package com.lylbp.project.controller.bg;

import com.lylbp.core.annotation.CheckPermission;
import com.lylbp.project.qo.PermissionQO;
import com.lylbp.project.vo.PermissionVO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import cn.hutool.core.bean.BeanUtil;

import java.util.List;
import java.util.Map;

import com.lylbp.core.entity.ResResult;
import com.lylbp.common.utils.ResResultUtil;
import com.lylbp.project.service.PermissionService;
import com.lylbp.project.entity.Permission;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;

/**
 * <p>
 * 权限管理-权限表  前端控制器
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
@RestController
@RequestMapping("/bg/permission")
@Api(tags = "权限相关")
public class PermissionController {
    @Resource
    private PermissionService service;

    ///////////////////////////////////////////////权限相关//////////////////////////////////////////////////////////////

    @PostMapping(value = "/getAllList")
    @ApiOperation("权限相关-获取所有权限列表")
    public ResResult<List<PermissionVO>> getList(@RequestBody PermissionQO query) {
        Map<String, Object> params = BeanUtil.beanToMap(query);
        List<PermissionVO> list = service.getPermissionVOListByParams(null, params);

        return ResResultUtil.success(list);
    }

    @PostMapping("/editPermissionData")
    @ApiOperation("权限相关-更新权限源数据")
    @CheckPermission(description = "权限相关-更新权限源数据")
    @Transactional(rollbackFor = Exception.class)
    public ResResult<Boolean> editPermissionData() {
        List<Permission> permissions = service.getAllAllPermissionByAnnotation();
        return ResResultUtil.success(service.editPermissionData(permissions));
    }
}

