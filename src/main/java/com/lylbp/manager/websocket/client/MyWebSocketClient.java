package com.lylbp.manager.websocket.client;

import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.enums.ReadyState;
import org.java_websocket.handshake.ServerHandshake;

import javax.annotation.PostConstruct;
import java.net.URI;

@Slf4j
public class MyWebSocketClient extends WebSocketClient {
    public MyWebSocketClient(URI uri) {
        super(uri);
    }

    @PostConstruct
    private void init() {
        try {
            this.connectBlocking();
            //开启心跳重连线程
            new Thread(() -> {
                while (true) {
                    try {
                        //当连接状态不为open时每5秒重连一次
                        if (getReadyState() != ReadyState.OPEN) {
                            reconnectBlocking();
                            Thread.sleep(5 * 1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        heartCheck();
        log.debug("与云服务器成功建立WS连接");
    }

    @Override
    public void onMessage(String s) {
        heartCheck();
        log.debug("-------- 接收到服务端数据： " + s + "--------");
        send("发送一个心跳成功");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        log.warn("readyState:" + getReadyState() + "," + code + "-reason:" + reason + ",与云服务器断开了连接,尝试重连...");
    }

    @Override
    public void onError(Exception e) {
        log.error("webSocket发生异常: {}:{}", e.getClass().getName(), e.getMessage());
        close();
    }

    public void heartCheck() {
        if (getReadyState() == ReadyState.OPEN) {
            send("....发送一个心跳.....");
        }
    }
}
