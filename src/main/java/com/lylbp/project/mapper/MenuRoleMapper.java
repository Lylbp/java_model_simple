package com.lylbp.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lylbp.project.entity.MenuRole;
import com.lylbp.project.vo.MenuRoleVO;
import com.lylbp.project.vo.MenuVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * 权限管理-菜单与角色关系表  Mapper 接口
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {
    /**
     * 条件查询List<MenuRoleVO>
     *
     * @param params 查询参数
     * @return List<MenuRoleVO>
     */
    List<MenuRoleVO> queryMenuRoleVOByParams(@Param("page") Page<MenuRoleVO> page, @Param("params") Map<String, Object> params);

    /**
     * 获取菜单集合
     *
     * @param roleId   角色id
     * @param isAssign 是否分配
     * @param params   参数
     * @return List<MenuVO>
     */
    List<MenuVO> queryRoleAssignMenuList(@Param("roleId") String roleId, @Param("isAssign") String isAssign,
                                         @Param("params") Map<String, Object> params);
}
