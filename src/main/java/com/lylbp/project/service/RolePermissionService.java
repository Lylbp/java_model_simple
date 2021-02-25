package com.lylbp.project.service;

import com.lylbp.project.entity.RolePermission;
import com.lylbp.project.vo.PermissionVO;
import com.lylbp.project.vo.RolePermissionVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

/**
 * <p>
 * 权限管理-角色与权限关系表  服务类
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
public interface RolePermissionService extends IService<RolePermission> {
    /**
     * 新增或编辑
     *
     * @param entity
     * @return Boolean
     */
    Boolean edit(RolePermission entity);

    /**
     * 通过查询参数获取列表
     *
     * @param page   分页对象,传null代表不分页
     * @param params 参数
     * @return List<RolePermissionVO>
     */
    List<RolePermissionVO> getRolePermissionVOListByParams(Page<RolePermissionVO> page, Map<String, Object> params);

    /**
     * 通过查询参数获取单个RolePermissionVO对象
     *
     * @param params 参数
     * @return RolePermissionVO
     */
    RolePermissionVO getOneRolePermissionVOByParams(Map<String, Object> params);

    /**
     * 通过字段名称与值获取单个RolePermissionVO对象
     *
     * @param columnName  名称
     * @param columnValue 值
     * @return RolePermissionVO
     */
    RolePermissionVO getOneRolePermissionVOBy(String columnName, Object columnValue);

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
    Boolean columnHasRepeat(String notId, SFunction<RolePermission, ?> columnName, Object columnValue);

    /**
     * 根据角色id以及权限集合批量添加
     *
     * @param roleId           角色id
     * @param permissionIdList 权限id集合
     * @return
     */
    Boolean batchInsert(String roleId, List<String> permissionIdList);


    /**
     * 根据角色id以及权限集合批量删除
     *
     * @param roleId           角色id
     * @param permissionIdList 权限id集合
     * @return Integer
     */
    Integer batchDelete(String roleId, List<String> permissionIdList);

    /**
     * 角色id以及权限id获取集合
     *
     * @param roleId       角色id
     * @param permissionId 权限id
     * @return
     */
    List<RolePermissionVO> getListByRoleIdAndPermissionId(String roleId, String permissionId);

    /**
     * 角色id分配权限查询
     *
     * @param roleId   角色id
     * @param isAssign 是否已分配
     * @param params   参数
     * @return List<PermissionVO>
     */
    List<PermissionVO> getRoleAssignPermissionList(String roleId, String isAssign, Map<String, Object> params);
}
