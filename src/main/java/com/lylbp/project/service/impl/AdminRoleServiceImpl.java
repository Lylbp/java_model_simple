package com.lylbp.project.service.impl;

import com.lylbp.common.enums.ResResultEnum;
import com.lylbp.core.exception.ResResultException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lylbp.project.entity.AdminRole;
import com.lylbp.project.mapper.AdminRoleMapper;
import com.lylbp.project.service.AdminRoleService;
import com.lylbp.project.service.AdminService;
import com.lylbp.project.service.RoleService;
import com.lylbp.project.vo.AdminRoleVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import java.util.HashMap;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import javax.annotation.Resource;

/**
 * <p>
 * 权限管理-管理员与角色关系表  服务实现类
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
@Service
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole> implements AdminRoleService {
    @Resource
    private AdminService adminService;

    @Resource
    private RoleService roleService;

    @Override
    public Boolean edit(AdminRole entity) {
        return saveOrUpdate(entity);
    }

    @Override
    public List<AdminRoleVO> getAdminRoleVOListByParams(Page<AdminRoleVO> page, Map<String, Object> params) {
        return getBaseMapper().queryAdminRoleVOByParams(page, params);
    }

    @Override
    public AdminRoleVO getOneAdminRoleVOByParams(Map<String, Object> params) {
        AdminRoleVO entityVO = null;
        List<AdminRoleVO> list = getAdminRoleVOListByParams(null, params);
        if (ObjectUtil.isNotEmpty(list)) {
            entityVO = list.get(0);
        }

        return entityVO;
    }

    @Override
    public AdminRoleVO getOneAdminRoleVOBy(String columnName, Object columnValue) {
        HashMap<String, Object> params = new HashMap<>();
        params.put(columnName, columnValue);

        return getOneAdminRoleVOByParams(params);
    }

    @Override
    public Boolean isExist(String id) {
        return ObjectUtil.isNotEmpty(this.getById(id));
    }

    @Override
    public Boolean columnHasRepeat(String notId, SFunction<AdminRole, ?> columnName, Object columnValue) {
        LambdaQueryWrapper<AdminRole> wrapper = new QueryWrapper<AdminRole>().lambda().eq(columnName, columnValue);
        wrapper.eq(AdminRole::getIsValid, 1);

        if (StrUtil.isNotEmpty(notId)) {
            wrapper.ne(AdminRole::getAdminRoleId, notId);
        }
        List<AdminRole> entities = getBaseMapper().selectList(wrapper);

        return ObjectUtil.isNotEmpty(entities);
    }

    @Override
    public Boolean batchInsert(String adminId, List<String> roleIdList) {
        if (ObjectUtil.isEmpty(adminService.getById(adminId))) {
            throw new ResResultException(ResResultEnum.ADMIN_USER_NO_EXIST);
        }

        //批量新增
        List<AdminRole> userRoles = new ArrayList<>();
        roleIdList.forEach(roleId -> {
            if (ObjectUtil.isEmpty(roleService.getById(roleId))) {
                throw new ResResultException(ResResultEnum.NO_ROLE_EXIT);
            }

            //通过角色id与用户id获取所有的数据
            List<AdminRoleVO> list = getListByUserIdAndRoleId(adminId, roleId);
            if (ObjectUtil.isNotEmpty(list)) {
                throw new ResResultException(ResResultEnum.USER_ROLE_EXIT);
            }

            AdminRole userRole = new AdminRole();
            userRole.setRoleId(roleId);
            userRole.setAdminId(adminId);

            userRoles.add(userRole);
        });

        //todo 删除redis中的权限数据
        return saveBatch(userRoles);
    }

    @Override
    public int batchDelete(String adminId, List<String> roleIdList) {
        return getBaseMapper().delete(new LambdaQueryWrapper<AdminRole>().eq(AdminRole::getAdminId, adminId)
                .in(AdminRole::getRoleId, roleIdList));
    }

    @Override
    public List<AdminRoleVO> getListByUserIdAndRoleId(String userId, String roleId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("roleId", roleId);

        return getAdminRoleVOListByParams(null, params);
    }


    @Override
    public List<String> getAllRoleIdListByAdminId(String adminId) {
        HashMap<String, Object> params = new HashMap<>(1);
        params.put("adminId", adminId);
        List<AdminRoleVO> list = getAdminRoleVOListByParams(null, params);

        return list.stream().map(AdminRoleVO::getRoleId).collect(Collectors.toList());
    }


}
