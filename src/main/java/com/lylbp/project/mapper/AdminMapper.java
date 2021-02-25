package com.lylbp.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lylbp.project.entity.Admin;
import com.lylbp.project.vo.AdminVO;
import com.lylbp.project.vo.MenuVO;
import com.lylbp.project.vo.PermissionVO;
import com.lylbp.project.vo.RoleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * 后台管理员表 Mapper 接口
 * </p>
 *
 * @author weiwenbin
 * @since 2020-07-01
 */
public interface AdminMapper extends BaseMapper<Admin> {
    /**
     * 条件查询List<AdminVO>
     *
     * @param params 查询参数
     * @return List<AdminVO>
     */
    List<AdminVO> queryAdminVOByParams(@Param("page") Page<AdminVO> page, @Param("params") Map<String, Object> params);


    /**
     * 批量修改用户账号状态
     *
     * @param adminIdList id集合
     * @param isEnabled   是否启用
     * @return
     */
    Boolean batchUpdateIsEnabledByIds(@Param("adminIdList") List<String> adminIdList, @Param("isEnabled") String isEnabled);

    /**
     * 用户分配权限查询
     *
     * @param adminId  用户id
     * @param isAssign 是否已分配
     * @param params   查询参数
     * @return List<PermissionVO>
     */
    List<PermissionVO> queryUserAssignPermissionList(@Param("adminId") String adminId, @Param("isAssign") String isAssign,
                                                     @Param("params") Map<String, Object> params);

    /**
     * 用户分配角色查询
     *
     * @param adminId  用户id
     * @param isAssign 是否已分配
     * @param params   查询参数
     * @return List<RoleVO>
     */
    List<RoleVO> queryUserAssignRoleList(@Param("adminId") String adminId, @Param("isAssign") String isAssign,
                                         @Param("params") Map<String, Object> params);

    /**
     * 用户分配菜单查询
     *
     * @param adminId  用户id
     * @param isAssign 是否已分配
     * @param params   查询参数
     * @return List<MenuVO>
     */
    List<MenuVO> queryUserAssignMenuList(@Param("adminId") String adminId, @Param("isAssign") String isAssign,
                                         @Param("params") Map<String, Object> params);
}
