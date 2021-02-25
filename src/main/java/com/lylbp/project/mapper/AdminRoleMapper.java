package com.lylbp.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lylbp.project.entity.AdminRole;
import com.lylbp.project.vo.AdminRoleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * 权限管理-管理员与角色关系表  Mapper 接口
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {
    /**
     * 条件查询List<AdminRoleVO>
     *
     * @param params 查询参数
     * @return List<AdminRoleVO>
     */
    List<AdminRoleVO> queryAdminRoleVOByParams(@Param("page") Page<AdminRoleVO> page,
                                               @Param("params") Map<String, Object> params);
}
