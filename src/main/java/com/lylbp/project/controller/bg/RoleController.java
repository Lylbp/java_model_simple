package com.lylbp.project.controller.bg;

import cn.hutool.core.util.ObjectUtil;
import com.lylbp.common.enums.ResResultEnum;
import com.lylbp.common.utils.ResResultUtil;
import com.lylbp.core.annotation.CheckPermission;
import com.lylbp.core.entity.PageResResult;
import com.lylbp.project.dto.MenuRoleBatchEditDTO;
import com.lylbp.project.dto.RoleDTO;
import com.lylbp.project.dto.RolePermissionBatchEditDTO;
import com.lylbp.project.entity.Role;
import com.lylbp.project.enums.TrueOrFalseEnum;
import com.lylbp.project.qo.RoleAssignMenuQO;
import com.lylbp.project.qo.RoleAssignPermissionQO;
import com.lylbp.project.qo.RoleQO;
import com.lylbp.project.service.MenuRoleService;
import com.lylbp.project.service.RolePermissionService;
import com.lylbp.project.service.RoleService;
import com.lylbp.project.vo.MenuVO;
import com.lylbp.project.vo.PermissionVO;
import com.lylbp.project.vo.RoleVO;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.hutool.core.bean.BeanUtil;

import java.util.List;
import java.util.Map;

import com.lylbp.core.entity.ResResult;

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
 * 权限管理-角色表  前端控制器
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
@RestController
@RequestMapping("/bg")
@Api(tags = "角色相关")
public class RoleController {
    @Resource
    private RoleService roleService;

    @Resource
    private RolePermissionService rolePermissionService;

    @Resource
    private MenuRoleService menuRoleService;

    /////////////////////////////////////////////角色相关////////////////////////////////////////////////////////////////

    @PostMapping(value = "/role/getPageList")
    @ApiOperation("角色-获取角色分页列表")
    @CheckPermission(description = "角色-获取角色分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页 默认1", defaultValue = "1", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页显示数 默认10", defaultValue = "10", dataType = "Integer", paramType = "query")
    })
    public ResResult<PageResResult<RoleVO>> getRolePageList(@RequestBody RoleQO query,
                                                            @RequestParam(defaultValue = "1") Integer current,
                                                            @RequestParam(defaultValue = "10") Integer size) {
        Map<String, Object> params = BeanUtil.beanToMap(query);

        Page<RoleVO> page = new Page<>(current, size);
        List<RoleVO> list = roleService.getRoleVOListByParams(page, params);
        page.setRecords(list);

        return ResResultUtil.makePageRsp(page);
    }


    @PostMapping(value = "/role/getAllList")
    @ApiOperation("角色-获取所有角色列表")
    public ResResult<List<RoleVO>> getRoleAllList(@RequestBody RoleQO query) {
        Map<String, Object> params = BeanUtil.beanToMap(query);
        List<RoleVO> list = roleService.getRoleVOListByParams(null, params);

        return ResResultUtil.success(list);
    }

    @GetMapping(value = "/role/info/{id}")
    @ApiOperation("角色-获取角色id获取角色详情")
    @CheckPermission(description = "角色-获取角色id获取角色详情")
    public ResResult<RoleVO> getRoleInfoById(@PathVariable(value = "id") @Validated @NotBlank String id) {
        RoleVO entity = roleService.getOneRoleVOBy("id", id);

        return ResResultUtil.success(entity);
    }

    @PostMapping(value = "/role/add")
    @ApiOperation("角色-添加")
    @CheckPermission(description = "角色-添加")
    public ResResult<Boolean> addRole(@RequestBody @Validated RoleDTO dto) {
        Role entity = new Role();
        BeanUtil.copyProperties(dto, entity);
        entity.setRoleId("");

        return ResResultUtil.success(roleService.edit(entity));
    }

    @PostMapping(value = "/role/edit")
    @ApiOperation("角色-编辑")
    @CheckPermission(description = "角色-编辑")
    public ResResult<Boolean> editRole(@RequestBody @Validated RoleDTO dto) {
        Role entity = new Role();
        BeanUtil.copyProperties(dto, entity);
        if (ObjectUtil.isEmpty(entity.getRoleId())) {
            return ResResultUtil.error(ResResultEnum.PARAM_ERROR);
        }

        return ResResultUtil.success(roleService.edit(entity));
    }

    @PostMapping(value = "/role/batchDeleteByIds")
    @ApiOperation("角色-通过角色id批量删除")
    @CheckPermission(description = "角色-通过角色id批量删除")
    public ResResult<Boolean> batchDeleteRoleByUserIds(@RequestBody @Validated @NotNull List<String> idList) {
        return ResResultUtil.success(roleService.removeByIds(idList));
    }

    /////////////////////////////////////////////角色与权限关系///////////////////////////////////////////////////////////

    @PostMapping("/rolePermission/batchInsert")
    @ApiOperation("角色与权限关系-批量添加角色与权限关系")
    @CheckPermission(description = "角色与权限关系-批量添加角色与权限关系")
    public ResResult<Boolean> rolePermissionBatchInsert(@RequestBody @Validated RolePermissionBatchEditDTO dto) {
        return ResResultUtil.success(rolePermissionService.batchInsert(dto.getRoleId(), dto.getPermissionIdList()));
    }

    @PostMapping("/rolePermission/batchDelete")
    @ApiOperation("角色与权限关系-批量删除角色与权限关系")
    @CheckPermission(description = "角色与权限关系-批量删除角色与权限关系")
    public ResResult<Integer> rolePermissionBatchDelete(@RequestBody @Validated RolePermissionBatchEditDTO dto) {
        return ResResultUtil.success(rolePermissionService.batchDelete(dto.getRoleId(), dto.getPermissionIdList()));
    }

    @ApiOperation("角色与权限关系-根据角色id获得未分配权限列表")
    @PostMapping("/rolePermission/getUserNoAssignRoleList")
    @CheckPermission(description = "角色与权限关系-根据角色id获得未分配权限列表")
    public ResResult<List<PermissionVO>> getRoleNoAssignRoleList(@RequestBody @Validated RoleAssignPermissionQO qo) {
        Map<String, Object> params = BeanUtil.beanToMap(qo);
        List<PermissionVO> list = rolePermissionService.getRoleAssignPermissionList(qo.getRoleId(),
                TrueOrFalseEnum.FALSE.getCode(), params);

        return ResResultUtil.success(list);
    }

    @PostMapping("/rolePermission/getRoleAssignData")
    @ApiOperation("角色与权限关系-根据角色id获得已分配权限列表")
    @CheckPermission(description = "角色与权限关系-根据角色id获得已分配权限列表")
    public ResResult<List<PermissionVO>> getUserHasAssignRoleList(@RequestBody @Validated RoleAssignPermissionQO qo) {
        Map<String, Object> params = BeanUtil.beanToMap(qo);
        List<PermissionVO> list = rolePermissionService.getRoleAssignPermissionList(qo.getRoleId(),
                TrueOrFalseEnum.TRUE.getCode(), params);

        return ResResultUtil.success(list);
    }

    /////////////////////////////////////////////角色与菜单关系////////////////////////////////////////////////////////////

    @PostMapping("/menuRole/batchInsert")
    @ApiOperation("角色与菜单关系-批量添加角色与菜单关系")
    @CheckPermission(description = "角色与菜单关系-批量添加角色与菜单关系")
    public ResResult<Boolean> menuRoleBatchInsert(@RequestBody @Validated MenuRoleBatchEditDTO dto) {
        return ResResultUtil.success(menuRoleService.batchInsert(dto.getRoleId(), dto.getMenuIdList()));
    }

    @PostMapping("/menuRole/batchDelete")
    @ApiOperation("角色与菜单关系-批量删除角色与菜单关系")
    @CheckPermission(description = "角色与菜单关系-批量删除角色与菜单关系")
    public ResResult<Integer> menuRoleBatchDelete(@RequestBody @Validated MenuRoleBatchEditDTO dto) {
        return ResResultUtil.success(menuRoleService.batchDelete(dto.getRoleId(), dto.getMenuIdList()));
    }

    @PostMapping("/menuRole/getRoleHasAssignMenuList")
    @ApiOperation("角色与菜单关系-根据角色id获得已分配菜单列表")
    @CheckPermission(description = "角色与菜单关系-根据角色id获得已分配菜单列表")
    public ResResult<List<MenuVO>> getRoleHasAssignMenuList(@RequestBody @Validated RoleAssignMenuQO qo) {
        Map<String, Object> params = BeanUtil.beanToMap(qo);

        return ResResultUtil.success(menuRoleService.getRoleAssignMenuList(qo.getRoleId(),
                TrueOrFalseEnum.TRUE.getCode(), params));
    }

    @PostMapping("/menuRole/getRoleNoAssignMenuList")
    @ApiOperation("角色与菜单关系-根据角色id获得未分配菜单列表")
    @CheckPermission(description = "角色与菜单关系-根据角色id获得未分配菜单列表")
    public ResResult<List<MenuVO>> getRoleNoAssignMenuList(@RequestBody @Validated RoleAssignMenuQO qo) {
        Map<String, Object> params = BeanUtil.beanToMap(qo);

        return ResResultUtil.success(menuRoleService.getRoleAssignMenuList(qo.getRoleId(),
                TrueOrFalseEnum.FALSE.getCode(), params));
    }


}

