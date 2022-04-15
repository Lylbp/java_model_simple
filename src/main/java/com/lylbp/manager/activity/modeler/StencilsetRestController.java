
package com.lylbp.manager.activity.modeler;

import io.swagger.annotations.Api;
import org.activiti.engine.ActivitiException;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;


/**
 * 这个控制器的接口无需登录权限
 *
 * @author weiwenbin
 */
@RestController
@RequestMapping(value = "/modeler/editor")
@Api(tags = "StencilsetRest相关(前端无需接入)")
public class StencilsetRestController {
    /**
     * 获取stencilset.json
     *
     * @return String
     */
    @GetMapping(value = "/stencilset", produces = "application/json;charset=utf-8")
    public @ResponseBody String getStencilset() {
        //stencilset.json为Model中的工具栏的名称字符，这里在resources下面查找
        InputStream stencilsetStream = this.getClass().getClassLoader().getResourceAsStream("stencilset.json");
        try {
            return IOUtils.toString(stencilsetStream, "utf-8");
        } catch (Exception e) {
            throw new ActivitiException("Error while loading stencil set", e);
        }
    }
}
