package com.lylbp.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lylbp.common.enums.ResResultEnum;
import com.lylbp.core.exception.ResResultException;
import com.lylbp.core.properties.ProjectProperties;
import com.lylbp.project.entity.Admin;
import com.lylbp.project.enums.TrueOrFalseEnum;
import com.lylbp.project.service.AdminService;
import com.lylbp.project.vo.AdminVO;
import com.lylbp.project.mapper.AdminMapper;
import com.lylbp.project.vo.MenuVO;
import com.lylbp.project.vo.PermissionVO;
import com.lylbp.project.vo.RoleVO;
import org.springframework.security.crypto.password.PasswordEncoder;
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
 * 后台管理员表 服务实现类
 * </p>
 *
 * @author weiwenbin
 * @since 2020-07-01
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private ProjectProperties projectProperties;

    @Override
    public Boolean edit(Admin entity) {
        String adminId = entity.getAdminId();
        Boolean hasRepeat = this.columnHasRepeat(adminId, Admin::getLoginAccount, entity.getLoginAccount());
        if (hasRepeat) {
            throw new ResResultException(ResResultEnum.ACTION_ADMIN_PHONE_EXIT);
        }

        //添加时设置默密码
        if (StrUtil.isEmpty(adminId) || ObjectUtil.isEmpty(getById(adminId))) {
            entity.setPwd(passwordEncoder.encode(entity.getLoginAccount()));
        }

        return saveOrUpdate(entity);
    }

    @Override
    public List<AdminVO> getAdminVOListByParams(Page<AdminVO> page, Map<String, Object> params) {
        return getBaseMapper().queryAdminVOByParams(page, params);
    }

    @Override
    public AdminVO getOneAdminVOByParams(Map<String, Object> params) {
        AdminVO entityVO = null;
        List<AdminVO> list = getAdminVOListByParams(null, params);
        if (ObjectUtil.isNotEmpty(list)) {
            entityVO = list.get(0);
        }

        return entityVO;
    }

    @Override
    public AdminVO getOneAdminVOBy(String columnName, Object columnValue) {
        HashMap<String, Object> params = new HashMap<>();
        params.put(columnName, columnValue);

        return getOneAdminVOByParams(params);
    }

    @Override
    public Boolean isExist(String id) {
        return ObjectUtil.isNotEmpty(this.getById(id));
    }

    @Override
    public Boolean columnHasRepeat(String notId, SFunction<Admin, ?> columnName, Object columnValue) {
        LambdaQueryWrapper<Admin> wrapper = new QueryWrapper<Admin>().lambda().eq(columnName, columnValue);
        wrapper.eq(Admin::getIsValid, TrueOrFalseEnum.TRUE.getCode());

        if (StrUtil.isNotEmpty(notId)) {
            wrapper.ne(Admin::getAdminId, notId);
        }
        List<Admin> entities = getBaseMapper().selectList(wrapper);

        return ObjectUtil.isNotEmpty(entities);
    }

    @Override
    public Boolean batchUpdateIsEnabledByIds(List<String> adminIdList, String isEnabled) {
        return getBaseMapper().batchUpdateIsEnabledByIds(adminIdList, isEnabled);
    }

    @Override
    public void listHasSupper(List<String> adminIdList, String superAdminUserId) {
        if (adminIdList.contains(superAdminUserId)) {
            Admin supperAdmin = getById(superAdminUserId);
            String content = String.format(
                    ResResultEnum.ACTION_ADMIN_USER_IS_SUPPER.getMsg() + "[用户名:%s,账号:%s]",
                    supperAdmin.getRealName(),
                    supperAdmin.getLoginAccount()
            );
            throw new ResResultException(ResResultEnum.ACTION_ADMIN_USER_IS_SUPPER.getCode(), content);
        }
    }

    @Override
    public Boolean isSupperAdmin(String currentAdminId) {
        return currentAdminId.equals(projectProperties.getSuperAdminId());
    }

    @Override
    public List<PermissionVO> getUserAssignPermissionVO(String adminId, String isAssign, Map<String, Object> params) {
        return getBaseMapper().queryUserAssignPermissionList(adminId, isAssign, params);
    }

    @Override
    public List<RoleVO> getUserAssignRoleList(String adminId, String isAssign, Map<String, Object> params) {
        return getBaseMapper().queryUserAssignRoleList(adminId, isAssign, params);
    }

    @Override
    public List<MenuVO> getUserAssignMenuList(String adminId, String isAssign, Map<String, Object> params) {
        return getBaseMapper().queryUserAssignMenuList(adminId, isAssign, params);
    }
}
