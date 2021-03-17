package com.lylbp.manager.websocket.constant;

/**
 * websocket长量
 *
 * @Author weiwenbin
 * @Date 2020/7/20 下午3:57
 */
public class WebSocketConstant {
    /**
     * 发送所有人
     */
    public static final String SEND_ALL_USER = "ALL";

    /**
     * redis订阅通道名称
     */
    public static final String REDIS_TOPIC = "chat";

    /**
     * 信息来源于后端服务器
     */
    public static final String MESSAGE_FROM_PING = "ping";

    /**
     * 信息来源于前端服务
     */
    public static final String MESSAGE_FROM_PONG = "pong";
}
