package com.lylbp.manager.jpush.service;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import com.lylbp.common.entity.ResResult;
import com.lylbp.manager.jpush.config.JPushConfig;
import com.lylbp.manager.jpush.enums.PlatformEnum;

import java.util.Map;

/**
 * JPUshService
 *
 * @author weiwenbin
 * @date 2020/5/28 上午9:50
 */

public interface JPUshService {
    /**
     * 设置配置对象
     *
     * @param jPushConfig 配置对象
     */
    void setJPushConfig(JPushConfig jPushConfig);

    /**
     * 获取配置对象
     *
     * @return JPushConfig 配置对象
     */
    JPushConfig getJPushConfig();

    /**
     * 获取推送连接对象
     *
     * @return JPushClient
     */
    JPushClient getJPushClient();


    /**
     * 确认平台
     *
     * @param platformEnum 平台枚举
     * @return Platform
     */
    Platform ensurePlatform(PlatformEnum platformEnum);

    /**
     * 按照别名推送用户
     * 若别名数据为空则代表推送给对应平台的所有用户
     *
     * @param platformEnum 平台枚举
     * @param title        标题
     * @param alert        内容
     * @param alias        别名
     * @param extras       额外内容
     * @return PushPayload
     */
    PushPayload getAliasPushPayload(PlatformEnum platformEnum, String title, String alert, String[] alias, Map<String, String> extras);

    /**
     * 所有平台，所有设备，内容为 ALERT 的通知
     *
     * @param alert 内容
     * @return PushPayload
     */
    PushPayload getAllAlertPayload(String alert);

    /**
     * 发送并解析结果集
     *
     * @param pushPayload pushPayload
     * @return ResResult<Boolean>
     */
    ResResult<Boolean> sendPushAndParseResult(PushPayload pushPayload);
}
