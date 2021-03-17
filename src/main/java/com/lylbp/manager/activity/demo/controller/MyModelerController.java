package com.lylbp.manager.activity.demo.controller;


import com.lylbp.common.utils.ResResultUtil;
import com.lylbp.common.entity.DataPage;
import com.lylbp.common.entity.PageResResult;
import com.lylbp.common.entity.ResResult;
import com.lylbp.manager.activity.demo.qo.ModelQueryQO;
import com.lylbp.manager.activity.service.ModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.repository.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 自定义模型
 * 这个控制器的接口要登录权限
 *
 * @author weiwenbin
 * @date 2020/12/14 下午4:03
 */
@Controller
@RequestMapping(value = "/myModeler")
@Api(tags = "自定义模型相关")
public class MyModelerController {
    @Resource
    private ModelService modelService;

    @Resource
    private HttpServletResponse response;

    @GetMapping("/create")
    @ApiOperation("创建流程模型")
    @ResponseBody
    public ResResult<String> createModel() {
        //设置一些默认信息
        String modelName = "new-process";
        String description = "";
        String modelKey = "process";

        return ResResultUtil.success(modelService.createModel(modelName, modelKey, description));
    }

    @GetMapping("/createAndRedirect")
    @ApiOperation("创建流程模型并跳转页面")
    @ResponseBody
    public void createAndRedirect() throws IOException {
        //设置一些默认信息
        String modelName = "new-process";
        String description = "";
        String modelKey = "process";

        String modelId = modelService.createModel(modelName, modelKey, description);
        response.sendRedirect("/modeler/modeler.html?modelId=" + modelId);
    }

    @GetMapping(value = "/deploy/{modelId}")
    @ApiOperation("部署流程模型")
    @ResponseBody
    public ResResult<Boolean> deployModel(@PathVariable String modelId) {
        modelService.deployModel(modelId);
        return ResResultUtil.success(true);
    }

    @DeleteMapping("/remove/{modelId}")
    @ApiOperation("删除模型")
    @ResponseBody
    public ResResult<Boolean> removeByModelIds(@PathVariable("modelId") String modelId) {
        return ResResultUtil.success(modelService.removeByModelId(modelId));
    }

    @PostMapping(value = "/list")
    @ApiOperation("获取分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页 默认1", defaultValue = "1", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页显示数 默认10", defaultValue = "10", dataType = "Integer", paramType = "query")
    })
    @ResponseBody
    public ResResult<PageResResult<Model>> list(@RequestBody ModelQueryQO qo,
                                                @RequestParam(defaultValue = "1") Integer current,
                                                @RequestParam(defaultValue = "10") Integer size) {
        DataPage<Model> page = new DataPage<>(current, size);
        List<Model> list = modelService.list(qo, page);
        page.setRecords(list);

        return ResResultUtil.makePageRsp(page);
    }

    @PostMapping(value = "/listNoPage")
    @ApiOperation("获取所有列表")
    @ResponseBody
    public ResResult<List<Model>> listNoPage(@RequestBody ModelQueryQO query) {
        return ResResultUtil.success(modelService.list(query, null));
    }

    @PostMapping(value = "/getModelInfo/{modelId}")
    @ApiOperation("获取模型详情")
    @ResponseBody
    public ResResult<Model> getModelInfo(@PathVariable("modelId") String modelId) {
        return ResResultUtil.success(modelService.getModelInfo(modelId));
    }
}
