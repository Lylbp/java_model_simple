package com.lylbp.project.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.lylbp.common.annotation.CheckPermission;
import com.lylbp.common.constant.ProjectConstant;
import com.lylbp.common.enums.ResResultEnum;
import com.lylbp.common.exception.ResResultException;
import com.lylbp.common.utils.AnnotationUtil;
import com.lylbp.project.entity.Permission;
import com.lylbp.manager.redis.service.RedisService;
import com.lylbp.project.enums.TrueOrFalseEnum;
import com.lylbp.project.vo.PermissionVO;
import com.lylbp.project.mapper.PermissionMapper;
import com.lylbp.project.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import javax.annotation.Resource;

/**
 * <p>
 * 权限管理-权限表  服务实现类
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Resource
    private RedisService redisService;

    @Override
    public Boolean edit(Permission entity) {
        return saveOrUpdate(entity);
    }

    @Override
    public List<PermissionVO> getPermissionVOListByParams(Page<PermissionVO> page, Map<String, Object> params) {
        return getBaseMapper().queryPermissionVOByParams(page, params);
    }

    @Override
    public PermissionVO getOnePermissionVOByParams(Map<String, Object> params) {
        PermissionVO entityVO = null;
        List<PermissionVO> list = getPermissionVOListByParams(null, params);
        if (ObjectUtil.isNotEmpty(list)) {
            entityVO = list.get(0);
        }

        return entityVO;
    }

    @Override
    public PermissionVO getOnePermissionVOBy(String columnName, Object columnValue) {
        HashMap<String, Object> params = new HashMap<>(16);
        params.put(columnName, columnValue);

        return getOnePermissionVOByParams(params);
    }

    @Override
    public Boolean isExist(String id) {
        return ObjectUtil.isNotEmpty(this.getById(id));
    }

    @Override
    public Boolean columnHasRepeat(String notId, SFunction<Permission, ?> columnName, Object columnValue) {
        LambdaQueryWrapper<Permission> wrapper = new QueryWrapper<Permission>().lambda().eq(columnName, columnValue);
        wrapper.eq(Permission::getIsValid, TrueOrFalseEnum.TRUE.getCode());

        if (StrUtil.isNotEmpty(notId)) {
            wrapper.ne(Permission::getPermissionId, notId);
        }
        List<Permission> entities = getBaseMapper().selectList(wrapper);

        return ObjectUtil.isNotEmpty(entities);
    }

    @Override
    public List<Permission> getAllAllPermissionByAnnotation() {
        AnnotationUtil annotationUtil = new AnnotationUtil();
        try {
            Map<String, Map<String, Object>> allAddTagAnnotationUrl = annotationUtil.getAllAddTagAnnotationUrl(
                    "classpath*:com/lylbp/oa/controller/bg/**/*.class", CheckPermission.class
            );

            List<Permission> permissions = new ArrayList<>();
            allAddTagAnnotationUrl.forEach((s, stringObjectMap) -> {
                Permission permission = new Permission();
                permission.setPermissionName((String) stringObjectMap.get("description"));
                permission.setPermissionUrl(s);

                permissions.add(permission);
            });

            return permissions;
        } catch (Exception e) {
            throw new ResResultException(ResResultEnum.SYSTEM_ERR.getCode(), e.getMessage());
        }
    }

    @Override
    public Boolean editPermissionData(List<Permission> permissions) {
        List<String> permissionUrlList = permissions.stream().map(Permission::getPermissionUrl).collect(Collectors.toList());
        try {
            //遍历当url一致时编辑,不一致时添加
            for (Permission permission : permissions) {
                String permissionUrl = permission.getPermissionUrl();
                permission.setIsValid(TrueOrFalseEnum.TRUE.getCode());

                Permission dbPermission = getOne(
                        new LambdaQueryWrapper<Permission>().eq(Permission::getPermissionUrl, permissionUrl));
                if (ObjectUtil.isEmpty(dbPermission)) {
                    save(permission);
                } else {
                    permission.setPermissionId(dbPermission.getPermissionId());
                    updateById(permission);
                }
            }

            //删除数据库存在但是注解中不存在的权限
            LambdaQueryWrapper<Permission> wrapper =
                    new QueryWrapper<Permission>().lambda().notIn(Permission::getPermissionUrl, permissionUrlList);
            getBaseMapper().delete(wrapper);

            //删除缓存
            deleRedisAllPermissionVO();
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public List<PermissionVO> getRedisAllPermissionVO() {
        String redisKey = ProjectConstant.REDIS_ALL_PERMISSION;
        String redisStr = redisService.strGet(redisKey);
        if (ObjectUtil.isEmpty(redisStr) || !JSONUtil.isJsonArray(redisStr) || JSONUtil.parseArray(redisStr).size() == 0) {
            List<PermissionVO> permissionVOList = getPermissionVOListByParams(null, new HashMap<>(1));
            redisService.strSet(redisKey, JSON.toJSONString(permissionVOList), 0L);

            return permissionVOList;
        } else {
            HashMap<String, Collection<ConfigAttribute>> map = new HashMap<>(16);
            List<PermissionVO> permissionVOS = JSONUtil.toList(JSONUtil.parseArray(redisStr), PermissionVO.class);


            return permissionVOS;
        }
    }

    @Override
    public Boolean deleRedisAllPermissionVO() {
        String redisKey = ProjectConstant.REDIS_ALL_PERMISSION;
        return redisService.delete(redisKey);
    }
}
