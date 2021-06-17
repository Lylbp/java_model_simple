package com.lylbp.manager.websocket.controller;


import com.lylbp.core.configure.ServerConfig;
import com.lylbp.manager.websocket.enums.PlatformEnum;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;


@Controller
@RequestMapping("/test/websocket")
@Api(tags = "测试websocket")
public class TestWebsocketController {
    @Resource
    private ServerConfig serverConfig;

    @RequestMapping("/index")
    public String index(Model model) {
        String userFlag = String.format("%s-%s-%s-%s-", System.currentTimeMillis(), PlatformEnum.WEB.getCode(), "A", "1");
        String wsUrl = String.format("ws://%s/websocket/", serverConfig.getUrl().replace("http://", ""));
        model.addAttribute("url", wsUrl);
        model.addAttribute("userFlag", userFlag);
        return "pages/index";
    }
}
