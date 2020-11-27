package com.lylbp.project.controller.bg;

import cn.hutool.core.util.ObjectUtil;
import com.lylbp.common.enums.ResResultEnum;
import com.lylbp.core.entity.PageResResult;
import com.lylbp.project.qo.TaskQO;
import com.lylbp.project.vo.TaskVO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.hutool.core.bean.BeanUtil;

import java.util.List;
import java.util.Map;

import com.lylbp.core.entity.ResResult;
import com.lylbp.common.utils.ResResultUtil;
import com.lylbp.project.service.TaskService;
import com.lylbp.project.entity.Task;
import com.lylbp.project.dto.TaskDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.annotation.Resource;

/**
 * <p>
 * 系统定时任务表  前端控制器
 * </p>
 *
 * @author weiwenbin
 * @since 2020-10-16
 */
@RestController
@RequestMapping("/bg/task")
@Api(tags = "系统定时任务表 ")
public class TaskController {
    @Resource
    private TaskService service;

    @PostMapping(value = "/getList")
    @ApiOperation("获取列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页 默认1", defaultValue = "1", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页显示数 默认10", defaultValue = "10", dataType = "Integer", paramType = "query")
    })
    public ResResult<PageResResult<TaskVO>> getList(@RequestBody TaskQO query,
                                                    @RequestParam(defaultValue = "1") Integer current,
                                                    @RequestParam(defaultValue = "10") Integer size) {
        Map<String, Object> params = BeanUtil.beanToMap(query);
        Page<TaskVO> page = new Page<>(current, size);
        List<TaskVO> list = service.getTaskVOListByParams(page, params);
        page.setRecords(list);

        return ResResultUtil.makePageRsp(page);
    }

    @GetMapping(value = "/{taskId}")
    @ApiOperation("通过主键获取信息[主键值 一般为id]")
    public ResResult<TaskVO> getInfoById(@PathVariable(value = "taskId") @Validated @NotBlank String taskId) {
        TaskVO entity = service.getOneTaskVOBy("taskId", taskId);

        return ResResultUtil.success(entity);
    }

    @PostMapping(value = "/add")
    @ApiOperation("添加")
    @Transactional(rollbackFor = Exception.class)
    public ResResult<Boolean> add(@RequestBody @Validated TaskDTO dto) {
        Task entity = new Task();
        BeanUtil.copyProperties(dto, entity);
        entity.setTaskId("");

        return ResResultUtil.success(service.edit(entity));
    }

    @PostMapping(value = "/edit")
    @ApiOperation("编辑")
    @Transactional(rollbackFor = Exception.class)
    public ResResult<Boolean> edit(@RequestBody @Validated TaskDTO dto) {
        Task entity = new Task();
        BeanUtil.copyProperties(dto, entity);
        if (ObjectUtil.isEmpty(entity.getTaskId())) {
            return ResResultUtil.error(ResResultEnum.PARAM_ERROR);
        }

        return ResResultUtil.success(service.edit(entity));
    }

    @PostMapping(value = "/batchPause")
    @ApiOperation("批量暂停")
    @Transactional(rollbackFor = Exception.class)
    public ResResult<Boolean> batchPauseByTaskIds(@RequestBody @NotNull List<String> taskIdList) {
        return ResResultUtil.success(service.batchPauseByTaskIds(taskIdList));
    }


    @PostMapping(value = "/batchResumeByTaskIds")
    @ApiOperation("批量启动")
    @Transactional(rollbackFor = Exception.class)
    public ResResult<Boolean> batchResumeByTaskIds(@RequestBody @NotNull List<String> taskIdList) {
        return ResResultUtil.success(service.batchResumeByTaskIds(taskIdList));
    }


    @PostMapping(value = "/runNow")
    @ApiOperation("立即执行")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", dataType = "String", paramType = "form"),
    })
    @Transactional(rollbackFor = Exception.class)
    public ResResult<Boolean> runNow(@RequestParam @NotBlank String taskId) {
        return ResResultUtil.success(service.runNow(taskId));
    }

    @PostMapping(value = "/batchDeleteByIds")
    @ApiOperation("通过主键批量删除")
    @Transactional(rollbackFor = Exception.class)
    public ResResult<Boolean> batchDeleteByTaskIds(@RequestBody @NotNull List<String> taskIdList) {
        return ResResultUtil.success(service.batchDeleteByTaskIds(taskIdList));
    }

    @PostMapping(value = "/batchDeleteByExecutionClassName")
    @ApiOperation("通过执行类名批量删除")
    @Transactional(rollbackFor = Exception.class)
    public ResResult<Boolean> batchDeleteByExecutionClassNameList(@RequestBody @NotNull List<String> executionClassNameList) {
        return ResResultUtil.success(service.batchDeleteByExecutionClassNames(executionClassNameList));
    }
}

