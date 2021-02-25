package com.lylbp.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lylbp.project.entity.RolePermission;
import com.lylbp.project.vo.PermissionVO;
import com.lylbp.project.vo.RolePermissionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * 权限管理-角色与权限关系表  Mapper 接口
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    /**
     * 条件查询List<RolePermissionVO>
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List<RolePermissionVO>
     */
    List<RolePermissionVO> queryRolePermissionVOByParams(@Param("page") Page<RolePermissionVO> page,
                                                         @Param("params") Map<String, Object> params);


    /**
     * 获取角色分配权限
     *
     * @param roleId   角色id
     * @param isAssign 是否分配
     * @param params   参数
     * @return List<PermissionVO>
     */
    List<PermissionVO> queryRoleAssignPermissionList(@Param("roleId") String roleId, @Param("isAssign") String isAssign,
                                                     @Param("params") Map<String, Object> params);
}
