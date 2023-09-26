package com.lylbp.project.controller.test2;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weiwenbin
 * @date 2020/7/1 下午2:38
 */
@RestController
@RequestMapping("/bg/test")
@Api(tags = "测试相关")
public class Test2Controller {
    @ApiOperation(value = "添加")
    @PostMapping(value = "/processCustom/Addtest")
    public Boolean add(@RequestBody @Validated ProcessCustomAddForm form) {
        return true;
    }
}
