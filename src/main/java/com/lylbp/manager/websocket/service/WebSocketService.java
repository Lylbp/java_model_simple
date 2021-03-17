package com.lylbp.manager.websocket.service;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.lylbp.common.enums.ResResultEnum;
import com.lylbp.common.exception.ResResultException;
import com.lylbp.core.configure.ServerConfig;
import com.lylbp.manager.websocket.constant.WebSocketConstant;
import com.lylbp.manager.websocket.entity.SuccessSendUserInfo;
import com.lylbp.manager.websocket.entity.UserSession;
import com.lylbp.manager.websocket.entity.WSMessage;
import com.lylbp.manager.websocket.entity.WebsocketKeyInfo;
import com.lylbp.manager.websocket.enums.PlatformEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * webSocket
 *
 * @author weiwenbin
 */
@ServerEndpoint(value = "/websocket/{username}")
@Component
@Slf4j
public class WebSocketService {
    /**
     * redis模版对象
     */
    private static StringRedisTemplate stringRedisTemplate;

    /**
     * 服务器host配置
     */
    private static ServerConfig serverConfig;

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        WebSocketService.stringRedisTemplate = stringRedisTemplate;
    }

    @Autowired
    public void setServerConfig(ServerConfig serverConfig) {
        WebSocketService.serverConfig = serverConfig;
    }

    /**
     * 当前服务器连接的session
     */
    public static final Map<String, Session> sessionMap = new ConcurrentHashMap();

    /**
     * 当前服务器连接的UserSession
     */
    public static final Map<String, UserSession> userSessionMap = new HashMap<>(10);

    /**
     * 在线数
     */
    private static int onlineCount = 0;

    /**
     * 连接的session
     */
    private Session session;

    /**
     * 连接的用户
     */
    private String username;


    /**
     * 监听连接
     *
     * @param username 连接用户
     * @param session  对应session
     */
    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session) {
        this.username = username;
        this.session = session;
        //添加用户数
        addOnlineCount();
        //sessionMap
        sessionMap.put(username, this.session);
        //userSessionMap
        UserSession userSession = sessionToUserSession(username, this.session);
        userSessionMap.put(username, userSession);
    }

    @OnClose
    public void onClose() {
        sessionMap.remove(username);
        userSessionMap.remove(username);
        //减少当前用户数
        subOnlineCount();
    }

    /**
     * 接受到信息
     *
     * @param jsonWsMessage 信息对象
     */
    @OnMessage
    public void onMessage(String jsonWsMessage) {
        try {
            WSMessage wsMessage = checkJsonMessage(jsonWsMessage);
            if (null == wsMessage) {
                return;
            }
            String toUser = wsMessage.getToUser();
            if (!toUser.equalsIgnoreCase(WebSocketConstant.SEND_ALL_USER)) {
                sendMessageTo(wsMessage);
            } else {
                wsMessage.setToUser(WebSocketConstant.SEND_ALL_USER);
                sendMessageAll(wsMessage);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    /**
     * 监听到错误
     *
     * @param session session对象
     * @param error   错误
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /**
     * 发送单人消息
     * 连接格式 时间戳-平台-用户代号-接受的推送类型- 如1600674139328-web-013762-1,2,3-
     *
     * @param wsMessage WSMessage
     */
    public void sendMessageTo(WSMessage wsMessage) {
        try {
            wsMessage.setConvertAddress(serverConfig.getUrl());
            //发送
            send(wsMessage, false);
            //pm要求一个用户可以多端接受信息(a用户分别在web和app都要接到推送)顾无论怎样都提醒其他服务器开始查找
            //2020-12-16关闭集群
            stringRedisTemplate.convertAndSend(WebSocketConstant.REDIS_TOPIC, JSON.toJSONString(wsMessage));
        } catch (Exception e) {
            log.info(e.getMessage());
        }

    }

    /**
     * 发送所有人消息
     *
     * @param wsMessage 消息对象
     */
    public void sendMessageAll(WSMessage wsMessage) {
        wsMessage.setConvertAddress(serverConfig.getUrl());
        send(wsMessage, true);
        //2020-12-16关闭集群
        stringRedisTemplate.convertAndSend(WebSocketConstant.REDIS_TOPIC, JSON.toJSONString(wsMessage));
    }

    /**
     * 获取当前在线用户数量
     *
     * @return int
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 添加在线用户数字
     */
    public static synchronized void addOnlineCount() {
        onlineCount++;
    }

    /**
     * 减少当前用户数
     */
    public static synchronized void subOnlineCount() {
        onlineCount--;
    }

    /**
     * 获取连接用户sessionMap
     *
     * @return Map<String, Session>
     */
    public static synchronized Map<String, Session> getClients() {
        return sessionMap;
    }

    /**
     * 检测数据
     *
     * @param jsonWsMessage jsonWsMessage
     * @return WSMessage
     */
    private WSMessage checkJsonMessage(String jsonWsMessage) {
        WSMessage wsMessage = JSON.toJavaObject(JSON.parseObject(jsonWsMessage), WSMessage.class);
        if (ObjectUtil.isEmpty(wsMessage)) {
            return null;
        }

        return checkWsMessage(wsMessage);
    }

    /**
     * 检测数据
     *
     * @param wsMessage wsMessage
     * @return WSMessage
     */
    private WSMessage checkWsMessage(WSMessage wsMessage) {
        if (ObjectUtil.isEmpty(wsMessage.getMessage())) {
            return null;
        }

        if (ObjectUtil.isEmpty(wsMessage.getToUser())) {
            return null;
        }

        if (ObjectUtil.isEmpty(wsMessage.getPushType())) {
            return null;
        }

        //这里一定是前端发所以一定不会是空
        if (ObjectUtil.isEmpty(wsMessage.getFromUser())) {
            return null;
        }

        return wsMessage;
    }

    /**
     * 发送个人消息
     *
     * @param wsMessage 消息对象
     * @return 发送成功的UserSession集合
     */
    public static List<UserSession> send(WSMessage wsMessage, Boolean isSendAll) {
        try {
            //发送成功的userSession
            List<UserSession> successUserSessions = new ArrayList<>(10);
            //接受人
            String toUser = wsMessage.getToUser();
            //推送类型
            String pushType = wsMessage.getPushType();
            //所接受的平台
            List<PlatformEnum> platformEnums = wsMessage.getPlatformEnums();
            //要发送的UserSession集合
            List<UserSession> userSessions;
            if (!isSendAll) {
                userSessions = sessionHas(toUser, pushType);
            } else {
                userSessions = new ArrayList<>(userSessionMap.values());
            }
            //遍历发送
            for (UserSession userSession : userSessions) {
                if (platformEnums != null && platformEnums.size() > 0) {
                    if (!platformEnums.contains(userSession.getPlatformEnum())) {
                        continue;
                    }
                }

                //发送
                wsMessage.setIsSuccess(true);
                //成功发送的UserSessions
                successUserSessions.add(userSession);
                //追加发送成功人
                List<SuccessSendUserInfo> sendUserInfoList = wsMessage.getSendUserInfoList();
                SuccessSendUserInfo successSendUserInfo =
                        new SuccessSendUserInfo(userSession.getUserFlag(), userSession.getPlatformEnum());
                if (!sendUserInfoList.contains(successSendUserInfo)) {
                    sendUserInfoList.add(successSendUserInfo);
                }

                //发送的信息json
                String jsonWsMessage = JSON.toJSONString(wsMessage);
                Session session = userSession.getSession();
                session.getAsyncRemote().sendText(jsonWsMessage);

                log.info("[toUser]发送消息成功: {},给用户:{}", wsMessage.getMessage(), userSession.getUserName());
            }

            return successUserSessions;
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ArrayList<>(10);
        }
    }

    /**
     * 查找符合条件的session
     *
     * @param toUser   本次推送用户
     * @param pushType 本次推送类型
     * @return List<UserSession>
     */
    public static List<UserSession> sessionHas(String toUser, String pushType) {
        //本服务器已存储的session
        Set<Map.Entry<String, UserSession>> entries = userSessionMap.entrySet();
        //符合条件的连接对象
        List<UserSession> hasSessions = new ArrayList<>();
        //遍历查找
        for (Map.Entry<String, UserSession> entry : entries) {
            String key = entry.getKey();
            UserSession userSession = entry.getValue();

            if (StrUtil.isNotBlank(key) && (userSession.getUserFlag().equalsIgnoreCase(toUser))) {
                if (ObjectUtil.isEmpty(userSession.getPlatformEnum())) {
                    continue;
                }

                if (ObjectUtil.isEmpty(userSession.getPushTypeList())) {
                    continue;
                }

                if (!userSession.getPushTypeList().contains(pushType)) {
                    continue;
                }


                hasSessions.add(userSession);
            }
        }

        return hasSessions;
    }

    /**
     * session转UserSession
     *
     * @param key     链接的用户名
     * @param session session
     * @return UserSession
     */
    public static UserSession sessionToUserSession(String key, Session session) {
        WebsocketKeyInfo infoByKey = getInfoByKey(key);

        return new UserSession(key, session, infoByKey.getPlatformEnum(), infoByKey.getConnectDate(),
                infoByKey.getUserFlag(), infoByKey.getPushTypeList());
    }

    /**
     * 获取key中包含的信息
     *
     * @param key 链接的用户名
     * @return WebsocketKeyInfo
     */
    public static WebsocketKeyInfo getInfoByKey(String key) {
        String[] split = key.split("-");
        if (split.length != 4) {
            throw new ResResultException(ResResultEnum.SYSTEM_ERR.getCode(), "连接用户名格式错误（格式:时间戳-平台-用户代号-接受的推送类型-）");
        }
        //截取连接的时间
        String dateTimeStr = split[0];
        DateTime connectDate = DateUtil.date(Long.parseLong(dateTimeStr));

        //截取连接的平台
        String platform = split[1];
        PlatformEnum platformEnum = PlatformEnum.getEnumByCode(platform);

        //截取连接的平台
        String userFlag = split[2];

        //截取接受推送的类型
        String pushTypeStr = split[3];
        List<String> pushTypeList;
        if (ObjectUtil.isEmpty(pushTypeStr)) {
            pushTypeList = new ArrayList<>(1);
        } else {
            pushTypeList = Arrays.asList(pushTypeStr.split(","));
        }

        return new WebsocketKeyInfo(platformEnum, connectDate, userFlag, pushTypeList);
    }
}
