package com.lylbp.project.service;

import com.lylbp.project.entity.Task;
import com.lylbp.project.vo.TaskVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

/**
 * <p>
 * 系统定时任务表  服务类
 * </p>
 *
 * @author weiwenbin
 * @since 2020-10-19
 */
public interface TaskService extends IService<Task> {
    /**
     * 新增或编辑
     *
     * @param entity
     * @return Boolean
     */
    Boolean edit(Task entity);

    /**
     * 通过查询参数获取列表
     *
     * @param page   分页对象,传null代表不分页
     * @param params 参数
     * @return List<TaskVO>
     */
    List<TaskVO> getTaskVOListByParams(Page<TaskVO> page, Map<String, Object> params);

    /**
     * 通过查询参数获取单个TaskVO对象
     *
     * @param params 参数
     * @return TaskVO
     */
    TaskVO getOneTaskVOByParams(Map<String, Object> params);

    /**
     * 通过字段名称与值获取单个TaskVO对象
     *
     * @param columnName  名称
     * @param columnValue 值
     * @return TaskVO
     */
    TaskVO getOneTaskVOBy(String columnName, Object columnValue);

    /**
     * 通过主键判断是否存在
     *
     * @param id 主键
     * @return Boolean
     */
    Boolean isExist(String id);

    /**
     * 判断字段是否重复 true:重复 false:不重复
     *
     * @param notId       id
     * @param columnName  字段名称
     * @param columnValue 字段值
     * @return Boolean
     */
    Boolean columnHasRepeat(String notId, SFunction<Task, ?> columnName, Object columnValue);

    /**
     * 根据任务id集合批量移除
     *
     * @param taskIdList 任务id集合
     * @return Boolean
     */
    Boolean batchDeleteByTaskIds(List<String> taskIdList);

    /**
     * 根据任务执行类名批量移除任务
     *
     * @param executionClassNameList 执行类型名
     * @return Boolean
     */
    Boolean batchDeleteByExecutionClassNames(List<String> executionClassNameList);

    /**
     * 通过任务id批量暂停
     *
     * @param taskIdList 任务id集合
     * @return Boolean
     */
    Boolean batchPauseByTaskIds(List<String> taskIdList);

    /**
     * 通过任务id批量启动
     *
     * @param taskIdList 任务id集合
     * @return Boolean
     */
    Boolean batchResumeByTaskIds(List<String> taskIdList);

    /**
     * 立即执行
     *
     * @param taskId 任务id
     * @return Boolean
     */
    Boolean runNow(String taskId);
}
