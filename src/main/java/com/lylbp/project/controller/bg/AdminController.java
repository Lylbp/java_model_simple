package com.lylbp.project.controller.bg;

import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.lylbp.common.annotation.ActionLog;
import com.lylbp.common.annotation.CheckPermission;
import com.lylbp.common.constant.ProjectConstant;
import com.lylbp.common.enums.ResResultEnum;
import com.lylbp.common.utils.TokenUtil;
import com.lylbp.common.entity.PageResResult;
import com.lylbp.core.properties.ProjectProperties;
import com.lylbp.manager.security.core.config.SecurityProperties;
import com.lylbp.project.dto.AdminRoleBatchEditDTO;
import com.lylbp.project.entity.Menu;
import com.lylbp.project.entity.SecurityUser;
import com.lylbp.project.enums.TrueOrFalseEnum;
import com.lylbp.project.qo.AdminQO;
import com.lylbp.project.qo.UserAssignRoleQO;
import com.lylbp.project.service.AdminRoleService;
import com.lylbp.project.service.MenuService;
import com.lylbp.project.vo.AdminVO;
import com.lylbp.project.vo.MenuNodeVO;
import com.lylbp.project.vo.MenuVO;
import com.lylbp.project.vo.RoleVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.hutool.core.bean.BeanUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lylbp.common.entity.ResResult;
import com.lylbp.common.utils.ResResultUtil;
import com.lylbp.project.service.AdminService;
import com.lylbp.project.entity.Admin;
import com.lylbp.project.dto.AdminDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.annotation.Resource;

/**
 * <p>
 * 后台管理员 前端控制器
 * </p>
 *
 * @author weiwenbin
 * @since 2020-07-01
 */
@RestController
@RequestMapping("/bg")
@Api(tags = "管理员相关")
public class AdminController {
    @Resource
    private AdminService adminService;

    @Resource
    private AdminRoleService adminRoleService;

    @Resource
    private MenuService menuService;

    @Resource
    private ProjectProperties projectProperties;

    @Resource
    private SecurityProperties securityProperties;

    @Resource
    HttpServletRequest request;
    /////////////////////////////////////////////////管理员相关//////////////////////////////////////////////////////////

    @PostMapping(value = "/admin/getPageList")
    @ApiOperation("管理员-获取管理员分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页 默认1", defaultValue = "1", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页显示数 默认10", defaultValue = "10", dataType = "Integer", paramType = "query")
    })
    @CheckPermission(description = "管理员-获取管理员分页列表")
    @ActionLog(description = "管理员-获取管理员分页列表")
    public ResResult<IPage<AdminVO>> getList(@RequestBody AdminQO query,
                                             @RequestParam(defaultValue = "1") Integer current,
                                             @RequestParam(defaultValue = "10") Integer size) {
        Admin admin = new Admin();
        admin.setAdminId("123123");
        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>(admin);
        adminService.getBaseMapper().selectList(adminQueryWrapper);
        Func1<Menu, String> getMenuId = Menu::getMenuId;
        Map<String, Object> params = BeanUtil.beanToMap(query);

        Page<AdminVO> page = new Page<>(current, size);
        List<AdminVO> list = adminService.getAdminVOListByParams(page, params);
        page.setRecords(list);

        return ResResultUtil.success(page);
    }

    @PostMapping(value = "/admin/getAllList")
    @ApiOperation("管理员-获取所有管理员列表")
    public ResResult<List<AdminVO>> getAllList(@RequestBody AdminQO query) {
        Map<String, Object> params = BeanUtil.beanToMap(query);
        return ResResultUtil.success(adminService.getAdminVOListByParams(null, params));
    }

    @PreAuthorize(value = "hasRole('ROLE_adminInfo')")
    @GetMapping(value = "/admin/info/{id}")
    @ApiOperation("管理员-通过管理员id获取管理员信息")
    @CheckPermission(description = "管理员-通过管理员id获取管理员信息")
    public ResResult<AdminVO> getInfoById(@PathVariable(value = "id") @NotBlank String id) {
        return ResResultUtil.success(adminService.getOneAdminVOBy("adminId", id));
    }

    @PostMapping(value = "/admin/add")
    @ApiOperation("管理员-添加")
    @CheckPermission(description = "管理员-添加")
    public ResResult<Boolean> add(@RequestBody @Validated AdminDTO dto) {
        Admin entity = new Admin();
        BeanUtil.copyProperties(dto, entity);
        entity.setAdminId("");

        return ResResultUtil.success(adminService.edit(entity));
    }

    @PostMapping(value = "/admin/edit")
    @ApiOperation("管理员-编辑")
    @CheckPermission(description = "管理员-编辑")
    public ResResult<Boolean> edit(@RequestBody @Validated AdminDTO dto) {
        Admin entity = new Admin();
        BeanUtil.copyProperties(dto, entity);
        if (ObjectUtil.isEmpty(dto.getAdminId())) {
            return ResResultUtil.error(ResResultEnum.PARAM_ERROR);
        }

        return ResResultUtil.success(adminService.edit(entity));
    }

    @PostMapping(value = "/admin/batchDeleteByIds")
    @ApiOperation("管理员-根据管理员id批量删除")
    @CheckPermission(description = "管理员-根据管理员id批量删除")
    public ResResult<Boolean> batchDeleteByUserIds(@Validated @RequestBody @NotNull List<String> adminIdList) {
        adminService.listHasSupper(adminIdList, projectProperties.getSuperAdminId());
        return ResResultUtil.success(adminService.removeByIds(adminIdList));
    }

    @PostMapping(value = "/admin/batchUpdateIsEnableByIds")
    @ApiOperation("管理员-根据管理员id批量启用账号")
    @CheckPermission(description = "管理员-根据管理员id批量启用账号")
    public ResResult<Boolean> batchUpdateEnableByIds(@Validated @RequestBody @NotNull List<String> adminIdList) {
        adminService.listHasSupper(adminIdList, projectProperties.getSuperAdminId());
        return ResResultUtil.success(adminService.batchUpdateIsEnabledByIds(adminIdList, TrueOrFalseEnum.TRUE.getCode()));
    }

    @PostMapping(value = "/admin/batchUpdateDisableByIds")
    @ApiOperation("管理员-根据管理员id批量禁用账号")
    @CheckPermission(description = "管理员-根据管理员id批量禁用账号")
    public ResResult<Boolean> batchUpdateDisableByIds(@RequestBody @Validated @NotNull List<String> adminIdList) {
        adminService.listHasSupper(adminIdList, projectProperties.getSuperAdminId());
        return ResResultUtil.success(adminService.batchUpdateIsEnabledByIds(adminIdList, TrueOrFalseEnum.FALSE.getCode()));
    }

    @PostMapping("/admin/getAssignMenuTree")
    @ApiOperation("管理员-获得当前用户已分配的菜单树")
    public ResResult<List<MenuNodeVO>> getAssignMenuTree() {
        SecurityUser securityUser = TokenUtil.getClazzFromHeader(request, ProjectConstant.AUTHENTICATION, SecurityUser.class);
        String adminId = securityUser.getAdmin().getAdminId();
        //超级管理员或未开始权限验证查全部
        List<MenuVO> menuVOList;
        if (securityUser.getIsSupperAdmin() || !securityProperties.getEnabled()) {
            menuVOList = menuService.getMenuVOListByParams(null, new HashMap<>(1));
        } else {
            menuVOList = adminService.getUserAssignMenuList(adminId, TrueOrFalseEnum.TRUE.getCode(),
                    new HashMap<>(1));
        }

        return ResResultUtil.success(menuService.recursionHandleMenuVOList(menuVOList, "0"));
    }

    /////////////////////////////////////////////////管理员与角色关系//////////////////////////////////////////////////////

    @PostMapping("/adminRole/batchInsert")
    @ApiOperation("管理员与角色关系-批量添加管理员与角色关系")
    @CheckPermission(description = "管理员与角色关系-批量添加管理员与角色关系")
    public ResResult<Boolean> batchInsert(@RequestBody @Validated AdminRoleBatchEditDTO dto) {
        return ResResultUtil.success(adminRoleService.batchInsert(dto.getAdminId(), dto.getRoleIdList()));
    }

    @PostMapping("/adminRole/batchDelete")
    @ApiOperation("管理员与角色关系-批量删除管理员与角色关系")
    @CheckPermission(description = "管理员与角色关系-批量删除管理员与角色关系")
    public ResResult<Integer> batchDelete(@RequestBody @Validated AdminRoleBatchEditDTO dto) {
        return ResResultUtil.success(adminRoleService.batchDelete(dto.getAdminId(), dto.getRoleIdList()));
    }

    @ApiOperation("管理员与角色关系-获得对应用户未分配角色列表")
    @PostMapping("/adminRole/getUserNoAssignRoleList")
    @CheckPermission(description = "管理员与角色关系-获得对应用户未分配角色列表")
    public ResResult<List<RoleVO>> getUserNoAssignRoleList(@RequestBody @Validated UserAssignRoleQO qo) {
        Map<String, Object> params = BeanUtil.beanToMap(qo);
        List<RoleVO> list = adminService.getUserAssignRoleList(qo.getAdminId(), TrueOrFalseEnum.FALSE.getCode(), params);

        return ResResultUtil.success(list);
    }

    @PostMapping("/adminRole/getRoleAssignData")
    @ApiOperation("管理员与角色关系-获得对应用户已分配角色列表")
    @CheckPermission(description = "管理员与角色关系-获得对应用户已分配角色列表")
    public ResResult<List<RoleVO>> getUserHasAssignRoleList(@RequestBody @Validated UserAssignRoleQO qo) {
        Map<String, Object> params = BeanUtil.beanToMap(qo);
        List<RoleVO> list = adminService.getUserAssignRoleList(qo.getAdminId(), TrueOrFalseEnum.TRUE.getCode(), params);

        return ResResultUtil.success(list);
    }

}

