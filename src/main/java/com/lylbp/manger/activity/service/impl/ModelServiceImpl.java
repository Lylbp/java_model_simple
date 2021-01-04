package com.lylbp.manger.activity.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lylbp.core.entity.DataPage;
import com.lylbp.manger.activity.demo.qo.ModelQueryQO;
import com.lylbp.manger.activity.service.ModelService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;


/**
 * ModelService
 *
 * @author weiwenbin
 * @date 2020/12/7 下午3:32
 */
@Slf4j
@Service
public class ModelServiceImpl implements ModelService, ModelDataJsonConstants {
    @Resource
    private RepositoryService repositoryService;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public String createModel(String modelName, String modelKey, String description) {
        try {
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);
            Model modelData = repositoryService.newModel();

            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(MODEL_NAME, modelName);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            modelObjectNode.put(MODEL_DESCRIPTION, description);
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(modelName);
            modelData.setKey(modelKey);
            //创建默认版本号0,保存后才是1
            modelData.setVersion(0);

            //保存模型
            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));

            return modelData.getId();
        } catch (Exception e) {
            log.error(e.getMessage());
            return "";
        }

    }

    @Override
    public Boolean saveModel(String modelId, String modelName, String description, String jsonXml, String svgXml) {
        try {
            // 获取流程模型
            Model model = repositoryService.getModel(modelId);

            ObjectNode modelJson = (ObjectNode) objectMapper.readTree(model.getMetaInfo());

            modelJson.put(MODEL_NAME, modelName);
            modelJson.put(MODEL_DESCRIPTION, description);
            model.setMetaInfo(modelJson.toString());
            model.setName(modelName);
            model.setVersion(model.getVersion() + 1);

            repositoryService.saveModel(model);

            repositoryService.addModelEditorSource(model.getId(), jsonXml.getBytes("utf-8"));

            InputStream svgStream = new ByteArrayInputStream(svgXml.getBytes("utf-8"));
            TranscoderInput input = new TranscoderInput(svgStream);

            PNGTranscoder transcoder = new PNGTranscoder();
            // 设置输出
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(outStream);

            // 进行转换
            transcoder.transcode(input, output);
            final byte[] result = outStream.toByteArray();
            repositoryService.addModelEditorSourceExtra(model.getId(), result);
            outStream.close();
            return true;

        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public ProcessDefinition deployModel(String modelId) {
        try {
            //数据库保存的是模型的元数据，不是XMl格式--需要将元数据转换为XML格式，再进行部署
            Model model = repositoryService.getModel(modelId);
            String processName = model.getName() + ".bpmn20.xml";
            ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelId));
            //获取BpmnModel
            BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            //获取bytes
            byte[] bytes = new BpmnXMLConverter().convertToXML(bpmnModel, "UTF-8");
            //部署流程
            Deployment deployment = repositoryService
                    .createDeployment()
                    .name(model.getName())
                    .addString(processName, new String(bytes, "UTF-8"))
                    .deploy();

            //获取流程定义
            return repositoryService.createProcessDefinitionQuery()
                    .deploymentId(deployment.getId()).singleResult();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Boolean removeByModelId(String modelId) {
        try {
            repositoryService.deleteModel(modelId);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public ObjectNode getEditorJson(String modelId) {
        try {
            ObjectNode modelNode = null;
            Model model = repositoryService.getModel(modelId);
            if (model != null) {
                if (StringUtils.isNotEmpty(model.getMetaInfo())) {
                    modelNode = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
                } else {
                    modelNode = objectMapper.createObjectNode();
                    modelNode.put(MODEL_NAME, model.getName());
                }
                modelNode.put(MODEL_ID, model.getId());
                ObjectNode editorJsonNode = (ObjectNode) objectMapper.readTree(
                        new String(repositoryService.getModelEditorSource(model.getId()), "utf-8"));
                modelNode.put("model", editorJsonNode);
            }
            return modelNode;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Model> list(ModelQueryQO queryQO, DataPage<Model> page) {
        ModelQuery modelQuery = repositoryService.createModelQuery();
        modelQuery.orderByLastUpdateTime().desc();

        // 条件过滤
        if (ObjectUtil.isNotEmpty(queryQO.getKey())) {
            modelQuery.modelKey(queryQO.getKey());
        }

        if (ObjectUtil.isNotEmpty(queryQO.getNameLike())) {
            modelQuery.modelNameLike("%" + queryQO.getNameLike() + "%");
        }

        if (null == page) {
            return modelQuery.list();
        } else {
            int size = (int) page.getSize();
            int current = (int) page.getCurrent();
            long count = modelQuery.count();
            page.setTotal(count);

            return modelQuery.listPage((current - 1) * size, size);
        }

    }

    @Override
    public Model getModelInfo(String modelId) {
        return repositoryService.createModelQuery().modelId(modelId).singleResult();
    }
}
