package com.lylbp.manager.websocket.controller;


import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/test/websocket")
@Api(tags = "测试websocket")
public class TestWebsocketController {
    @RequestMapping("/index")
    @ResponseBody
    public ModelAndView index() {
        return new ModelAndView("pages/index");
    }
}
