package com.lylbp.project.service;

import com.lylbp.project.entity.AdminRole;
import com.lylbp.project.vo.AdminRoleVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

/**
 * <p>
 * 权限管理-管理员与角色关系表  服务类
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
public interface AdminRoleService extends IService<AdminRole> {
    /**
     * 新增或编辑
     *
     * @param entity
     * @return Boolean
     */
    Boolean edit(AdminRole entity);

    /**
     * 通过查询参数获取列表
     *
     * @param page   分页对象,传null代表不分页
     * @param params 参数
     * @return List<AdminRoleVO>
     */
    List<AdminRoleVO> getAdminRoleVOListByParams(Page<AdminRoleVO> page, Map<String, Object> params);

    /**
     * 通过查询参数获取单个AdminRoleVO对象
     *
     * @param params 参数
     * @return AdminRoleVO
     */
    AdminRoleVO getOneAdminRoleVOByParams(Map<String, Object> params);

    /**
     * 通过字段名称与值获取单个AdminRoleVO对象
     *
     * @param columnName  名称
     * @param columnValue 值
     * @return AdminRoleVO
     */
    AdminRoleVO getOneAdminRoleVOBy(String columnName, Object columnValue);

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
    Boolean columnHasRepeat(String notId, SFunction<AdminRole, ?> columnName, Object columnValue);

    /**
     * 批量添加用户角色
     *
     * @param adminId    用户id
     * @param roleIdList 角色id集合
     * @return Boolean
     */
    Boolean batchInsert(String adminId, List<String> roleIdList);

    /**
     * 批量删除用户角色
     *
     * @param adminId    用户id
     * @param roleIdList 角色id集合
     * @return int
     */
    int batchDelete(String adminId, List<String> roleIdList);

    /**
     * 通过用户id以及角色id查询列表
     *
     * @param adminId 用户id
     * @param roleId  角色id集合
     * @return
     */

    List<AdminRoleVO> getListByUserIdAndRoleId(String adminId, String roleId);

    /**
     * 通过用户id获取已分配的角色id集合
     *
     * @param adminId 用户id
     * @return List<String>
     */
    List<String> getAllRoleIdListByAdminId(String adminId);
}
