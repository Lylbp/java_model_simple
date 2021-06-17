package com.lylbp.manager.activity.modeler;


import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lylbp.manager.activity.service.ModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 这个控制器的接口无需登录权限
 *
 * @author weiwenbin
 */
@Controller
@RequestMapping(value = "/modeler/model")
@Api(tags = "模型相关(前端无需接入)")
@ConditionalOnProperty(prefix = "spring.activiti", name = "database-schema-update", havingValue = "true")
public class ModelerController {
    @Resource
    private ModelService modelService;
    ////////////////////页面接口[必须按照官方页面接口形式编写]//////////////////////

    @PostMapping(value = "/{modelId}/save")
    @ApiOperation("保存流程模型")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Boolean saveModel(@PathVariable String modelId, @RequestParam("name") String modeName,
                             @RequestParam("description") String description,
                             @RequestParam("json_xml") String jsonXml,
                             @RequestParam("svg_xml") String svgXml) {
        return modelService.saveModel(modelId, modeName, description, jsonXml, svgXml);
    }


    @GetMapping(value = "/{modelId}/json", produces = "application/json")
    @ApiOperation("获取模型json")
    @ResponseBody
    public ObjectNode getEditorJson(@PathVariable String modelId) {
        return modelService.getEditorJson(modelId);
    }

}
