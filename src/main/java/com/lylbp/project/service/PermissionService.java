package com.lylbp.project.service;

import com.lylbp.project.entity.Permission;
import com.lylbp.project.vo.PermissionVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

/**
 * <p>
 * 权限管理-权限表  服务类
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
public interface PermissionService extends IService<Permission> {
    /**
     * 新增或编辑
     *
     * @param entity 实体类
     * @return Boolean
     */
    Boolean edit(Permission entity);

    /**
     * 通过查询参数获取列表
     *
     * @param page   分页对象,传null代表不分页
     * @param params 参数
     * @return List<PermissionVO>
     */
    List<PermissionVO> getPermissionVOListByParams(Page<PermissionVO> page, Map<String, Object> params);

    /**
     * 通过查询参数获取单个PermissionVO对象
     *
     * @param params 参数
     * @return PermissionVO
     */
    PermissionVO getOnePermissionVOByParams(Map<String, Object> params);

    /**
     * 通过字段名称与值获取单个PermissionVO对象
     *
     * @param columnName  名称
     * @param columnValue 值
     * @return PermissionVO
     */
    PermissionVO getOnePermissionVOBy(String columnName, Object columnValue);

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
    Boolean columnHasRepeat(String notId, SFunction<Permission, ?> columnName, Object columnValue);

    /**
     * 获取所有加了注解的函数列表
     *
     * @return List<Permission>
     */
    List<Permission> getAllAllPermissionByAnnotation();


    /**
     * 更新权限表数据 存在则不编辑 不存在新增
     *
     * @param permissions 权限集合
     * @return Boolean
     */
    Boolean editPermissionData(List<Permission> permissions);

    /**
     * 获取redis中所有的权限对象
     *
     * @return List<PermissionVO>
     */
    List<PermissionVO> getRedisAllPermissionVO();

    /**
     * 删除redis中的权限缓存
     *
     * @return Boolean
     */
    Boolean delRedisAllPermissionVO();
}
