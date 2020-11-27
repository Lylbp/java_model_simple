package com.lylbp.project.service.impl;

import com.lylbp.common.enums.ResResultEnum;
import com.lylbp.core.exception.ResResultException;
import com.lylbp.project.entity.MenuRole;
import com.lylbp.project.entity.Role;
import com.lylbp.project.service.MenuService;
import com.lylbp.project.service.RoleService;
import com.lylbp.project.vo.MenuRoleVO;
import com.lylbp.project.mapper.MenuRoleMapper;
import com.lylbp.project.service.MenuRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lylbp.project.vo.MenuVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import java.util.HashMap;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import javax.annotation.Resource;

/**
 * <p>
 * 权限管理-菜单与角色关系表  服务实现类
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements MenuRoleService {
    @Resource
    private RoleService roleService;

    @Resource
    private MenuService menuService;

    @Override
    public Boolean edit(MenuRole entity) {
        return saveOrUpdate(entity);
    }

    @Override
    public List<MenuRoleVO> getMenuRoleVOListByParams(Page<MenuRoleVO> page, Map<String, Object> params) {
        return getBaseMapper().queryMenuRoleVOByParams(page, params);
    }

    @Override
    public MenuRoleVO getOneMenuRoleVOByParams(Map<String, Object> params) {
        MenuRoleVO entityVO = null;
        List<MenuRoleVO> list = getMenuRoleVOListByParams(null, params);
        if (ObjectUtil.isNotEmpty(list)) {
            entityVO = list.get(0);
        }

        return entityVO;
    }

    @Override
    public MenuRoleVO getOneMenuRoleVOBy(String columnName, Object columnValue) {
        HashMap<String, Object> params = new HashMap<>();
        params.put(columnName, columnValue);

        return getOneMenuRoleVOByParams(params);
    }

    @Override
    public Boolean isExist(String id) {
        return ObjectUtil.isNotEmpty(this.getById(id));
    }

    @Override
    public Boolean columnHasRepeat(String notId, SFunction<MenuRole, ?> columnName, Object columnValue) {
        LambdaQueryWrapper<MenuRole> wrapper = new QueryWrapper<MenuRole>().lambda().eq(columnName, columnValue);
        wrapper.eq(MenuRole::getIsValid, 1);

        if (StrUtil.isNotEmpty(notId)) {
            wrapper.ne(MenuRole::getMenuRoleId, notId);
        }
        List<MenuRole> entities = getBaseMapper().selectList(wrapper);

        return ObjectUtil.isNotEmpty(entities);
    }

    @Override
    public Boolean removeByMenuId(String menuId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("menu_id", menuId);
        return this.removeByMap(params);
    }

    @Override
    public Boolean resetMenuRoleByRoleId(String menuId, List<String> roleIds) {
        //1.先删除所有菜单
        removeByMenuId(menuId);

        //2.重新添加关系数据
        List<MenuRole> list = new ArrayList<>();
        roleIds.forEach(roleId -> {
            Role role = roleService.getById(roleId);
            if (ObjectUtil.isEmpty(role)) {
                throw new ResResultException(ResResultEnum.NO_ROLE_EXIT);
            }

            MenuRole menuRole = new MenuRole();
            menuRole.setMenuId(menuId);
            menuRole.setRoleId(roleId);
            list.add(menuRole);
        });

        return saveBatch(list);
    }

    @Override
    public List<MenuVO> getRoleAssignMenuList(String roleId, String isAssign, Map<String, Object> params) {
        return baseMapper.queryRoleAssignMenuList(roleId, isAssign, params);
    }

    private List<MenuRoleVO> getListByRoleIdAndMenuId(String roleId, String menuId) {
        HashMap<String, Object> params = new HashMap<>(2);
        params.put("roleId", roleId);
        params.put("menuId", menuId);

        return getMenuRoleVOListByParams(null, params);
    }

    @Override
    public Boolean batchInsert(String roleId, List<String> permissionIdList) {
        if (ObjectUtil.isEmpty(roleService.getById(roleId))) {
            throw new ResResultException(ResResultEnum.NO_ROLE_EXIT);
        }

        //批量新增
        List<MenuRole> menuRoles = new ArrayList<>();
        permissionIdList.forEach(menuId -> {
            if (ObjectUtil.isEmpty(menuService.getById(menuId))) {
                throw new ResResultException(ResResultEnum.NO_MENU_EXIT);
            }

            //通过角色id与权限id获取所有的数据
            List<MenuRoleVO> list = getListByRoleIdAndMenuId(roleId, menuId);
            if (ObjectUtil.isNotEmpty(list)) {
                throw new ResResultException(ResResultEnum.ROLE_MENU_EXIT);
            }

            MenuRole menuRole = new MenuRole();
            menuRole.setRoleId(roleId);
            menuRole.setMenuId(menuId);

            menuRoles.add(menuRole);
        });

        return saveBatch(menuRoles);
    }


    @Override
    public Integer batchDelete(String roleId, List<String> permissionIdList) {
        return getBaseMapper().delete(new LambdaQueryWrapper<MenuRole>().eq(MenuRole::getRoleId, roleId)
                .in(MenuRole::getMenuId, permissionIdList));
    }
}
