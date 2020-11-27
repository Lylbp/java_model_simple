package com.lylbp.project.service.impl;


import com.lylbp.common.enums.ResResultEnum;
import com.lylbp.core.exception.ResResultException;
import com.lylbp.project.entity.Role;
import com.lylbp.project.vo.RoleVO;
import com.lylbp.project.mapper.RoleMapper;
import com.lylbp.project.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import java.util.HashMap;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * <p>
 * 权限管理-角色表  服务实现类
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Override
    public Boolean edit(Role entity) {
        Boolean hasRepeat = columnHasRepeat(entity.getRoleId(), Role::getRoleName, entity.getRoleName());
        if (hasRepeat) {
            throw new ResResultException(ResResultEnum.ROLE_NAME_EXIT);
        }

        return saveOrUpdate(entity);
    }

    @Override
    public List<RoleVO> getRoleVOListByParams(Page<RoleVO> page, Map<String, Object> params) {
        return getBaseMapper().queryRoleVOByParams(page, params);
    }

    @Override
    public RoleVO getOneRoleVOByParams(Map<String, Object> params) {
        RoleVO entityVO = null;
        List<RoleVO> list = getRoleVOListByParams(null, params);
        if (ObjectUtil.isNotEmpty(list)) {
            entityVO = list.get(0);
        }

        return entityVO;
    }

    @Override
    public RoleVO getOneRoleVOBy(String columnName, Object columnValue) {
        HashMap<String, Object> params = new HashMap<>();
        params.put(columnName, columnValue);

        return getOneRoleVOByParams(params);
    }

    @Override
    public Boolean isExist(String id) {
        return ObjectUtil.isNotEmpty(this.getById(id));
    }

    @Override
    public Boolean columnHasRepeat(String notId, SFunction<Role, ?> columnName, Object columnValue) {
        LambdaQueryWrapper<Role> wrapper = new QueryWrapper<Role>().lambda().eq(columnName, columnValue);
        wrapper.eq(Role::getIsValid, 1);

        if (StrUtil.isNotEmpty(notId)) {
            wrapper.ne(Role::getRoleId, notId);
        }
        List<Role> entities = getBaseMapper().selectList(wrapper);

        return ObjectUtil.isNotEmpty(entities);
    }
}
