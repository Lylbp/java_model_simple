//package com.lylbp.manager.websocket.config;
//
//import com.lylbp.manager.websocket.client.MyWebSocketClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.server.standard.ServerEndpointExporter;
//
//import java.net.URI;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * WebSocketConfig
// *
// * @Author weiwenbin
// * @Date 2020/7/17 下午4:52
// */
//@Configuration
//public class WebSocketConfig {
//    @Bean
//    public ServerEndpointExporter serverEndpointExporter() {
//        return new ServerEndpointExporter();
//    }
//
//
//    /**
//     * 后端模拟websocket客户端
//     *
//     * @return MyWebSocketClient
//     */
//    @Bean
//    public MyWebSocketClient myWebSocketClient() {
//        Map<String, String> httpHeaders = new HashMap<>();
//        String wsUrl = "ws://localhost:8074/websocket/1723175939599-web-C-1-";
//        URI uri = URI.create(wsUrl);
//        return new MyWebSocketClient(uri, httpHeaders);
//    }
//}
