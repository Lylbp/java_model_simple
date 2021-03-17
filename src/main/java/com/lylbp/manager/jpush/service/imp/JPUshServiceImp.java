package com.lylbp.manager.jpush.service.imp;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.lylbp.common.entity.ResResult;
import com.lylbp.common.enums.ResResultEnum;
import com.lylbp.common.exception.ResResultException;
import com.lylbp.common.utils.ResResultUtil;
import com.lylbp.manager.jpush.config.JPushConfig;
import com.lylbp.manager.jpush.enums.PlatformEnum;
import com.lylbp.manager.jpush.service.JPUshService;

import java.util.Map;

/**
 * JPUshServiceImp
 *
 * @author weiwenbin
 * @date 2020/5/28 上午9:50
 */
public class JPUshServiceImp implements JPUshService {
    protected JPushConfig jPushConfig;

    private static JPushClient jPushClient;

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
        if (null == jPushClient) {
            try {
                jPushClient = new JPushClient(
                        jPushConfig.getJPushProperties().getMasterKey(),
                        jPushConfig.getJPushProperties().getAppKey()
                );
            } catch (Exception exception) {
                throw new ResResultException(ResResultEnum.SYSTEM_ERR.getCode(), "服务器无网络或jpush参数配置错误");
            }
        }
        return jPushClient;
    }

    @Override
    public Platform ensurePlatform(PlatformEnum platformEnum) {
        Platform platform;
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
            default:
                throw new ResResultException(ResResultEnum.SYSTEM_ERR);
        }
        return platform;
    }

    @Override
    public PushPayload getAliasPushPayload(PlatformEnum platformEnum, String title, String alert, String[] alias,
                                           Map<String, String> extras) {
        //确认平台
        Platform platform = ensurePlatform(platformEnum);
        PushPayload.Builder builder = PushPayload.newBuilder();
        //平台
        builder.setPlatform(platform);

        //若别名数据为空则代表推送给对应平台的所有用户
        Audience audience = Audience.all();
        if (alias.length != 0) {
            audience = Audience.alias(alias);
        }
        //指定别名
        builder.setAudience(audience);

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
                        //APNs 是否生产环境
                        .setApnsProduction(true)
                        .setTimeToLive(10 * 86400)
                        .build());

        return builder.build();
    }

    @Override
    public PushPayload getAllAlertPayload(String alert) {
        return PushPayload.alertAll(alert);
    }

    @Override
    public ResResult<Boolean> sendPushAndParseResult(PushPayload pushPayload) {
        PushResult pushResult;
        try {
            JPushClient client = getJPushClient();
            pushResult = client.sendPush(pushPayload);
        } catch (APIConnectionException | APIRequestException e) {
            return ResResultUtil.makeRsp(ResResultEnum.SYSTEM_ERR.getCode(), e.getMessage(), false);
        }

        if (pushResult.error != null) {
            return ResResultUtil.makeRsp(ResResultEnum.SYSTEM_ERR.getCode(), pushResult.error.getMessage(), false);
        }


        return ResResultUtil.success(true, "success");
    }
}
