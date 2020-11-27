package com.lylbp.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lylbp.project.entity.Task;
import com.lylbp.project.vo.TaskVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * 系统定时任务表  Mapper 接口
 * </p>
 *
 * @author weiwenbin
 * @since 2020-10-19
 */
public interface TaskMapper extends BaseMapper<Task> {
    /**
     * 条件查询List<TaskVO>
     *
     * @param params 查询参数
     * @return List<TaskVO>
     */
    List<TaskVO> queryTaskVOByParams(@Param("page") Page<TaskVO> page, @Param("params") Map<String, Object> params);
}
