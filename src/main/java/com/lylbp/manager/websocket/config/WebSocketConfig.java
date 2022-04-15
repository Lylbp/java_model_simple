package com.lylbp.manager.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebSocketConfig
 *
 * @Author weiwenbin
 * @Date 2020/7/17 下午4:52
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }


//    /**
//     * 后端模拟websocket客户端
//     *
//     * @return MyWebSocketClient
//     */
//    @Bean
//    public MyWebSocketClient myWebSocketClient() {
//        String wsUrl = "ws://192.168.150.129:8073/websocket/1624928718196-web-B-1-";
//        URI uri = URI.create(wsUrl);
//        return new MyWebSocketClient(uri);
//    }
}
