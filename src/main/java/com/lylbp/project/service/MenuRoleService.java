package com.lylbp.project.service;

import com.lylbp.project.entity.MenuRole;
import com.lylbp.project.vo.MenuRoleVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.lylbp.project.vo.MenuVO;

/**
 * <p>
 * 权限管理-菜单与角色关系表  服务类
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
public interface MenuRoleService extends IService<MenuRole> {
    /**
     * 新增或编辑
     *
     * @param entity
     * @return Boolean
     */
    Boolean edit(MenuRole entity);

    /**
     * 通过查询参数获取列表
     *
     * @param page   分页对象,传null代表不分页
     * @param params 参数
     * @return List<MenuRoleVO>
     */
    List<MenuRoleVO> getMenuRoleVOListByParams(Page<MenuRoleVO> page, Map<String, Object> params);

    /**
     * 通过查询参数获取单个MenuRoleVO对象
     *
     * @param params 参数
     * @return MenuRoleVO
     */
    MenuRoleVO getOneMenuRoleVOByParams(Map<String, Object> params);

    /**
     * 通过字段名称与值获取单个MenuRoleVO对象
     *
     * @param columnName  名称
     * @param columnValue 值
     * @return MenuRoleVO
     */
    MenuRoleVO getOneMenuRoleVOBy(String columnName, Object columnValue);

    /**
     * 通过主键判断是否存在
     *
     * @param id 主键
     * @return Boolean
     */
    Boolean isExist(String id);

    /**
     * 判断字段是否重复 true:重复 false:不重复
     *
     * @param notId       id
     * @param columnName  字段名称
     * @param columnValue 字段值
     * @return Boolean
     */
    Boolean columnHasRepeat(String notId, SFunction<MenuRole, ?> columnName, Object columnValue);

    /**
     * 根据菜单id删除所有菜单
     *
     * @param menuId 菜单id
     * @return Boolean
     */
    Boolean removeByMenuId(String menuId);

    /**
     * 重置角色与菜单关系 1.先删除所有菜单 2.重新添加关系数据
     *
     * @param menuId  菜单id
     * @param roleIds 角色集合
     * @return Boolean
     */
    Boolean resetMenuRoleByRoleId(String menuId, List<String> roleIds);

    /**
     * 角色分配菜单查询
     *
     * @param roleId   角色id
     * @param isAssign 是否分配
     * @param params   查询参数
     * @return List<MenuVO>
     */
    List<MenuVO> getRoleAssignMenuList(String roleId, String isAssign, Map<String, Object> params);

    /**
     * 批量新增菜单与角色关系
     *
     * @param roleId     角色id
     * @param menuIdList 菜单集合
     * @return Boolean
     */
    Boolean batchInsert(String roleId, List<String> menuIdList);

    /**
     * 批量删除菜单与角色关系
     *
     * @param roleId     角色id
     * @param menuIdList 菜单集合
     * @return Integer
     */
    Integer batchDelete(String roleId, List<String> menuIdList);
}
