package com.lylbp.manager.jpush.service.imp;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.lylbp.manager.jpush.config.JPushConfig;
import com.lylbp.manager.jpush.enums.PlatformEnum;
import com.lylbp.manager.jpush.service.JPUshService;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author weiwenbin
 * @date 2020/5/28 上午9:50
 */
public class JPUshServiceImp implements JPUshService {
    protected JPushConfig jPushConfig;

    @Resource
    private JPushClient jPushClient;

    @Override
    public void setJPushConfig(JPushConfig jPushConfig) {
        this.jPushConfig = jPushConfig;
    }

    @Override
    public JPushConfig getJPushConfig() {
        return this.jPushConfig;
    }


    @Override
    public JPushClient getJPushClient() {
        return jPushClient;
    }

    @Override
    public Platform ensurePlatform(PlatformEnum platformEnum) {
        Platform platform = Platform.all();
        switch (platformEnum) {
            case PLATFORM_ALL:
                platform = Platform.all();
                break;
            case PLATFORM_IOS:
                platform = Platform.ios();
                break;
            case PLATFORM_ANDROID:
                platform = Platform.android();
                break;
            case PLATFORM_IOS_ANDROID:
                platform = Platform.android_ios();
                break;
        }
        return platform;
    }

    @Override
    public PushPayload getAliasPushPayload(PlatformEnum platformEnum, String title, String alert, String[] alias,
                                           Map<String, String> extras) {
        //确认平台
        Platform platform = ensurePlatform(platformEnum);
        PushPayload.Builder builder = PushPayload.newBuilder();
        builder.setPlatform(platform);//平台

        //若别名数据为空则代表推送给对应平台的所有用户
        Audience audience = Audience.all();
        if (alias.length != 0) {
            audience = Audience.alias(alias);
        }
        builder.setAudience(audience);//指定别名

        builder
                .setNotification(Notification.newBuilder()
                        .setAlert(alert)
                        .addPlatformNotification(
                                AndroidNotification.newBuilder()
                                        .setTitle(title)
                                        .addExtras(extras)
                                        .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .addExtras(extras)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)//APNs 是否生产环境
                        .setTimeToLive((long) (10 * 86400))
                        .build());

        return builder.build();
    }

    @Override
    public PushPayload getAllAlertPayload(String alert) {
        return PushPayload.alertAll(alert);
    }
}
