package com.lylbp.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lylbp.project.entity.Role;
import com.lylbp.project.vo.RoleVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * 权限管理-角色表  Mapper 接口
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
    * 条件查询List<RoleVO>
    *
    * @param params 查询参数
    * @return List<RoleVO>
    */
    List<RoleVO> queryRoleVOByParams(@Param("page")Page<RoleVO> page, @Param("params") Map<String, Object> params);
}
