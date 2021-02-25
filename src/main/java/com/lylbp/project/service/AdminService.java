package com.lylbp.project.service;


import com.lylbp.project.entity.Admin;
import com.lylbp.project.vo.AdminVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.lylbp.project.vo.MenuVO;
import com.lylbp.project.vo.PermissionVO;
import com.lylbp.project.vo.RoleVO;

/**
 * <p>
 * 后台管理员表 服务类
 * </p>
 *
 * @author weiwenbin
 * @since 2020-07-01
 */
public interface AdminService extends IService<Admin> {
    /**
     * 新增或编辑
     *
     * @param entity 实体
     * @return Boolean
     */
    Boolean edit(Admin entity);

    /**
     * 通过查询参数获取列表
     *
     * @param page   分页对象,传null代表不分页
     * @param params 参数
     * @return List<AdminVO>
     */
    List<AdminVO> getAdminVOListByParams(Page<AdminVO> page, Map<String, Object> params);

    /**
     * 通过查询参数获取单个AdminVO对象
     *
     * @param params 参数
     * @return AdminVO
     */
    AdminVO getOneAdminVOByParams(Map<String, Object> params);

    /**
     * 通过字段名称与值获取单个AdminVO对象
     *
     * @param columnName  名称
     * @param columnValue 值
     * @return AdminVO
     */
    AdminVO getOneAdminVOBy(String columnName, Object columnValue);

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
    Boolean columnHasRepeat(String notId, SFunction<Admin, ?> columnName, Object columnValue);

    /**
     * 批量修改用户账号状态
     *
     * @param adminIdList id集合
     * @param isEnabled   是否启用
     * @return Boolean
     */
    Boolean batchUpdateIsEnabledByIds(List<String> adminIdList, String isEnabled);

    /**
     * 操作数据中是否含有超级管理员
     *
     * @param adminIdList      管理员id集合
     * @param superAdminUserId 超级管理员id
     */
    void listHasSupper(List<String> adminIdList, String superAdminUserId);

    /**
     * 是否是超级管理员
     *
     * @param currentAdminId 当前管理员id
     * @return Boolean
     */
    Boolean isSupperAdmin(String currentAdminId);

    /**
     * 查询用户分配权限列表
     *
     * @param adminId  用户id
     * @param isAssign 是否已分配
     * @param params   查询参数
     * @return List<Permission>
     */
    List<PermissionVO> getUserAssignPermissionVO(String adminId, String isAssign, Map<String, Object> params);

    /**
     * 查询用户分配角色列表
     *
     * @param adminId  用户id
     * @param isAssign 是否已分配
     * @param params   查询参数
     * @return List<RoleVO>
     */
    List<RoleVO> getUserAssignRoleList(String adminId, String isAssign, Map<String, Object> params);

    /**
     * 查询用户分配菜单列表
     *
     * @param adminId  用户id
     * @param isAssign 是否已分配
     * @param params   查询参数
     * @return List<MenuVO>
     */
    List<MenuVO> getUserAssignMenuList(String adminId, String isAssign, Map<String, Object> params);
}
