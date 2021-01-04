package com.lylbp.project.service.impl;

import com.lylbp.common.enums.ResResultEnum;
import com.lylbp.common.exception.ResResultException;
import com.lylbp.project.entity.RolePermission;
import com.lylbp.project.service.PermissionService;
import com.lylbp.project.service.RoleService;
import com.lylbp.project.vo.*;
import com.lylbp.project.mapper.RolePermissionMapper;
import com.lylbp.project.service.RolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import javax.annotation.Resource;

/**
 * <p>
 * 权限管理-角色与权限关系表  服务实现类
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {
    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;

    @Override
    public Boolean edit(RolePermission entity) {
        return saveOrUpdate(entity);
    }

    @Override
    public List<RolePermissionVO> getRolePermissionVOListByParams(Page<RolePermissionVO> page, Map<String, Object> params) {
        return getBaseMapper().queryRolePermissionVOByParams(page, params);
    }

    @Override
    public RolePermissionVO getOneRolePermissionVOByParams(Map<String, Object> params) {
        RolePermissionVO entityVO = null;
        List<RolePermissionVO> list = getRolePermissionVOListByParams(null, params);
        if (ObjectUtil.isNotEmpty(list)) {
            entityVO = list.get(0);
        }

        return entityVO;
    }

    @Override
    public RolePermissionVO getOneRolePermissionVOBy(String columnName, Object columnValue) {
        HashMap<String, Object> params = new HashMap<>();
        params.put(columnName, columnValue);

        return getOneRolePermissionVOByParams(params);
    }

    @Override
    public Boolean isExist(String id) {
        return ObjectUtil.isNotEmpty(this.getById(id));
    }

    @Override
    public Boolean columnHasRepeat(String notId, SFunction<RolePermission, ?> columnName, Object columnValue) {
        LambdaQueryWrapper<RolePermission> wrapper = new QueryWrapper<RolePermission>().lambda().eq(columnName, columnValue);
        wrapper.eq(RolePermission::getIsValid, 1);

        if (StrUtil.isNotEmpty(notId)) {
            wrapper.ne(RolePermission::getRolePermissionId, notId);
        }
        List<RolePermission> entities = getBaseMapper().selectList(wrapper);

        return ObjectUtil.isNotEmpty(entities);
    }

    @Override
    public Boolean batchInsert(String roleId, List<String> permissionIdList) {
        if (ObjectUtil.isEmpty(roleService.getById(roleId))) {
            throw new ResResultException(ResResultEnum.NO_ROLE_EXIT);
        }

        //批量新增
        List<RolePermission> rolePermissions = new ArrayList<>();
        permissionIdList.forEach(permissionId -> {
            if (ObjectUtil.isEmpty(permissionService.getById(permissionId))) {
                throw new ResResultException(ResResultEnum.NO_PERMISSION_EXIT);
            }

            //通过角色id与权限id获取所有的数据
            List<RolePermissionVO> list = getListByRoleIdAndPermissionId(roleId, permissionId);
            if (ObjectUtil.isNotEmpty(list)) {
                throw new ResResultException(ResResultEnum.ROLE_PERMISSION_EXIT);
            }

            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);

            rolePermissions.add(rolePermission);
        });

        //todo 删除redis中的权限数据
        return saveBatch(rolePermissions);
    }

    @Override
    public Integer batchDelete(String roleId, List<String> permissionIdList) {
        return getBaseMapper().delete(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleId, roleId)
                .in(RolePermission::getPermissionId, permissionIdList));
    }

    @Override
    public List<RolePermissionVO> getListByRoleIdAndPermissionId(String roleId, String permissionId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("roleId", roleId);
        params.put("permissionId", permissionId);

        return getRolePermissionVOListByParams(null, params);
    }

    @Override
    public List<PermissionVO> getRoleAssignPermissionList(String roleId, String isAssign, Map<String, Object> params) {
        return getBaseMapper().queryRoleAssignPermissionList(roleId, isAssign, params);
    }
}
