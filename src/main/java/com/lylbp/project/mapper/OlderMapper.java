package com.lylbp.project.mapper;

import com.lylbp.project.entity.Older;
import com.lylbp.project.vo.OlderVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author weiwenbin
 * @since 2022-10-31
 */
public interface OlderMapper extends BaseMapper<Older> {
        
    /**
    * 条件查询List<OlderVO>
    * @param page   分页参数
    * @param params 查询参数
    * @return List<OlderVO>
    */
    List<OlderVO> queryOlderVOByParams(@Param("page")Page<OlderVO> page, @Param("params") Map<String, Object> params);

    /**
     * 根据id编辑
     *
     * @param entity 对应实体 注意为null值不修改
     * @param id  id Id
     * @return Boolean
     */
    Boolean updateEntityById(@Param("entity")Older entity, @Param("id") String id);
}
