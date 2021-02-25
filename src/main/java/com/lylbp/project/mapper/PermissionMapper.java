package com.lylbp.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lylbp.project.entity.Permission;
import com.lylbp.project.vo.PermissionVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * 权限管理-权限表  Mapper 接口
 * </p>
 *
 * @author weiwenbin
 * @since 2020-06-30
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
    * 条件查询List<PermissionVO>
    *
    * @param params 查询参数
    * @return List<PermissionVO>
    */
    List<PermissionVO> queryPermissionVOByParams(@Param("page")Page<PermissionVO> page,
                                                 @Param("params") Map<String, Object> params);
}
