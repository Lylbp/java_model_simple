package com.lylbp.manager.websocket.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.lylbp.core.configure.ServerConfig;
import com.lylbp.manager.websocket.constant.WebSocketConstant;
import com.lylbp.manager.websocket.entity.WSMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * websocket集群接收信息
 *
 * @author alex
 */
@Component
@Slf4j
public class MessageReceiver {
    @Resource
    private ServerConfig serverConfig;

    /**
     * 接收消息的方法
     */
    public void receiveMessage(String jsonWsMessage) {
        try {
            if (StrUtil.isBlank(jsonWsMessage)) {
                return;
            }

            WSMessage wsMessage = JSON.toJavaObject(JSON.parseObject(jsonWsMessage), WSMessage.class);
            String toUser = wsMessage.getToUser();
            //同一台服务器同一个端口的服务不唤醒
            String convertAddress = wsMessage.getConvertAddress();
            if (ObjectUtil.isNotEmpty(convertAddress) && convertAddress.equalsIgnoreCase(serverConfig.getUrl())) {
                return;
            }

            if (!WebSocketConstant.SEND_ALL_USER.equalsIgnoreCase(toUser)) {
                sendMessageTo(wsMessage);
            } else {
                sendMessageAll(wsMessage);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    /**
     * 发送到个人
     *
     * @param wsMessage 消息对象
     */
    public void sendMessageTo(WSMessage wsMessage) {
        WebSocketService.send(wsMessage, false);
    }

    /**
     * redis订阅到后发送所有人
     *
     * @param wsMessage WSMessage
     */
    public void sendMessageAll(WSMessage wsMessage) {
        WebSocketService.send(wsMessage, true);

        String ipAddr = serverConfig.getUrl();
        log.info("[all]redis收到广播消息，已发送给{}服务所所连接人 ", ipAddr);
    }
}
