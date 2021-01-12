package com.lylbp.project.service.impl;

import com.lylbp.common.enums.ResResultEnum;
import com.lylbp.manager.quartz.util.QuartzUtils;
import com.lylbp.common.exception.ResResultException;
import com.lylbp.project.entity.Task;
import com.lylbp.project.enums.TaskStatusEnum;
import com.lylbp.project.enums.TrueOrFalseEnum;
import com.lylbp.project.vo.TaskVO;
import com.lylbp.project.mapper.TaskMapper;
import com.lylbp.project.service.TaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.quartz.Scheduler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import java.util.HashMap;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import javax.annotation.Resource;

/**
 * <p>
 * 系统定时任务表  服务实现类
 * </p>
 *
 * @author weiwenbin
 * @since 2020-10-19
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {
    @Resource
    private Scheduler scheduler;

    @Override
    public Boolean edit(Task entity) {
        String taskId = entity.getTaskId();
        //执行类名
        String executionClassName = entity.getExecutionClassName();
        //执行时间表达式
        String executionTime = entity.getExecutionTime();

        //验证执行类是否存在
        if (columnHasRepeat(taskId, Task::getExecutionClassName, entity.getExecutionClassName())) {
            throw new ResResultException(ResResultEnum.EXECUTION_CLASS_NAME_EXIT);
        }

        try {
            //1.操作库表
            boolean isSuccess = saveOrUpdate(entity);
            if (isSuccess) {
                //2.Quartz操作
                if (ObjectUtil.isEmpty(taskId)) {
                    //添加: quartz直接添加job
                    return QuartzUtils.addJob(scheduler, executionClassName, executionTime);
                } else {
                    //编辑任务: 暂停时直接暂停quartz的job、 启动时quartz的先删除原有job再新增job
                    String statusStr = String.valueOf(entity.getStatus());
                    if (TaskStatusEnum.SUSPEND.getCode().equals(statusStr)) {
                        return QuartzUtils.pauseJob(scheduler, executionClassName);
                    } else {
                        return QuartzUtils.deleteAndAddJob(scheduler, executionClassName, executionTime);
                    }
                }
            }
            return false;
        } catch (Exception exception) {
            throw new ResResultException(ResResultEnum.SYSTEM_ERR.getCode(), exception.getMessage());
        }

    }

    @Override
    public List<TaskVO> getTaskVOListByParams(Page<TaskVO> page, Map<String, Object> params) {
        return getBaseMapper().queryTaskVOByParams(page, params);
    }

    @Override
    public TaskVO getOneTaskVOByParams(Map<String, Object> params) {
        TaskVO entityVO = null;
        List<TaskVO> list = getTaskVOListByParams(null, params);
        if (ObjectUtil.isNotEmpty(list)) {
            entityVO = list.get(0);
        }

        return entityVO;
    }

    @Override
    public TaskVO getOneTaskVOBy(String columnName, Object columnValue) {
        HashMap<String, Object> params = new HashMap<>();
        params.put(columnName, columnValue);

        return getOneTaskVOByParams(params);
    }

    @Override
    public Boolean isExist(String id) {
        return ObjectUtil.isNotEmpty(this.getById(id));
    }

    @Override
    public Boolean columnHasRepeat(String notId, SFunction<Task, ?> columnName, Object columnValue) {
        LambdaQueryWrapper<Task> wrapper = new QueryWrapper<Task>().lambda().eq(columnName, columnValue);
        wrapper.eq(Task::getIsValid, TrueOrFalseEnum.TRUE.getCode());

        if (StrUtil.isNotEmpty(notId)) {
            wrapper.ne(Task::getTaskId, notId);
        }
        List<Task> entities = getBaseMapper().selectList(wrapper);

        return ObjectUtil.isNotEmpty(entities);
    }

    @Override
    public Boolean batchDeleteByTaskIds(List<String> idList) {
        idList.forEach(taskId -> {
            Task task = getById(taskId);
            if (ObjectUtil.isNotEmpty(task) && StrUtil.isNotEmpty(task.getExecutionClassName())) {
                Boolean isSuccess = removeById(taskId);
                //Quartz操作
                if (isSuccess) {
                    QuartzUtils.removeJob(scheduler, task.getExecutionClassName());
                }
            }
        });

        return true;
    }

    @Override
    public Boolean batchDeleteByExecutionClassNames(List<String> executionClassNameList) {
        executionClassNameList.forEach(executionClassName -> QuartzUtils.removeJob(scheduler, executionClassName));
        return true;
    }

    @Override
    public Boolean batchPauseByTaskIds(List<String> taskIdList) {
        taskIdList.forEach(taskId -> {
            Task task = getById(taskId);
            if (ObjectUtil.isNotEmpty(task) && StrUtil.isNotEmpty(task.getExecutionClassName())) {
                task.setStatus(Integer.parseInt(TaskStatusEnum.SUSPEND.getCode()));
                Boolean isSuccess = updateById(task);
                //Quartz操作
                if (isSuccess) {
                    QuartzUtils.pauseJob(scheduler, task.getExecutionClassName());
                }
            }
        });

        return true;
    }

    @Override
    public Boolean batchResumeByTaskIds(List<String> taskIdList) {
        taskIdList.forEach(taskId -> {
            Task task = getById(taskId);
            if (ObjectUtil.isNotEmpty(task) && StrUtil.isNotEmpty(task.getExecutionClassName())) {
                task.setStatus(Integer.parseInt(TaskStatusEnum.ENABLE.getCode()));
                Boolean isSuccess = updateById(task);
                //Quartz操作
                if (isSuccess) {
                    QuartzUtils.resumeJob(scheduler, task.getExecutionClassName());
                }
            }
        });

        return true;
    }

    @Override
    public Boolean runNow(String taskId) {
        Task sysTask = getById(taskId);
        if (ObjectUtil.isEmpty(sysTask) || StrUtil.isEmpty(sysTask.getExecutionTime())) {
            throw new ResResultException(ResResultEnum.SYSTEM_ERR);
        }

        return QuartzUtils.runNow(scheduler, sysTask.getExecutionClassName());
    }
}
