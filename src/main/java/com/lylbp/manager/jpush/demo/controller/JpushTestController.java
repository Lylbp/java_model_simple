package com.lylbp.manager.jpush.demo.controller;

import cn.jpush.api.push.model.PushPayload;
import com.lylbp.common.entity.ResResult;
import com.lylbp.manager.jpush.enums.PlatformEnum;
import com.lylbp.manager.jpush.service.JPUshService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author weiwenbin
 * @since 2020-12-17
 */
@RestController
@RequestMapping("/test/jpush")
@Api(tags = "jpushTest")
public class JpushTestController {
    @Resource
    private JPUshService jPUshService;

    @GetMapping("/sendTest")
    @ApiOperation("推送测试")
    public ResResult<Boolean> sendTest() {
        Map<String, String> extras = new HashMap<>(1);
        extras.put("test", "aaa");
        String[] alias = {"a1"};

        //推送
        PushPayload aliasPushPayload = jPUshService.getAliasPushPayload(
                PlatformEnum.PLATFORM_ALL,
                "你有一个新通知",
                "出了城,吃着火锅还唱着歌,突然就被麻匪劫了",
                alias,
                extras
        );

        return jPUshService.sendPushAndParseResult(aliasPushPayload);
    }
}

