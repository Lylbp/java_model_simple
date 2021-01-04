package com.lylbp.manager.activity.service;


import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lylbp.common.entity.DataPage;
import com.lylbp.manager.activity.demo.qo.ModelQueryQO;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;

import java.util.List;

/**
 * ModelService
 *
 * @author weiwenbin
 * @date 2020/12/7 下午3:32
 */
public interface ModelService {
    /**
     * 创建流程模型
     *
     * @param modelName   模型名称
     * @param modelKey    模型key
     * @param description 模型描述
     * @return String
     */
    String createModel(String modelName, String modelKey, String description);

    /**
     * 保存模型
     *
     * @param modelId     模型id
     * @param modelName   模型名称
     * @param description 模型描述
     * @param jsonXml     jsonXml
     * @param svgXml      svgXml
     * @return
     */
    Boolean saveModel(String modelId, String modelName, String description, String jsonXml, String svgXml);

    /**
     * 部署
     *
     * @param modelId 模型id
     * @return ProcessDefinition
     */
    ProcessDefinition deployModel(String modelId);

    /**
     * 删除
     *
     * @param modelId 模型id
     * @return Boolean
     */
    Boolean removeByModelId(String modelId);

    /**
     * 获取模型json
     *
     * @param modelId 模型id
     * @return ObjectNode
     */
    ObjectNode getEditorJson(String modelId);

    /**
     * 获取模型列表
     *
     * @param query 查询参数
     * @param page  分页
     * @return List<Model>
     */
    List<Model> list(ModelQueryQO query, DataPage<Model> page);

    /**
     * 获取模型
     *
     * @param modelId 模型id
     * @return Model
     */
    Model getModelInfo(String modelId);
}
