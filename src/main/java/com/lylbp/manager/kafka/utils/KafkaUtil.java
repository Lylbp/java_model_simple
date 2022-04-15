package com.lylbp.manager.kafka.utils;

import cn.hutool.json.JSONUtil;
import com.lylbp.common.enums.ResResultEnum;
import com.lylbp.common.exception.ResResultException;

import java.util.Optional;

/**
 * KafkaUtil
 *
 * @Author weiwenbin
 * @Date 2020/9/23 上午10:34
 */
public class KafkaUtil {
    public static String kafkaMsgHandle(Optional message, Boolean validateIsJson) {
        if (message.isPresent()) {
            String msg = (String) message.get();
            if (validateIsJson) {
                if (!JSONUtil.isJson(msg)) {
                    throw new ResResultException(ResResultEnum.SYSTEM_ERR);
                }
            }
            return msg;
        } else {
            throw new ResResultException(ResResultEnum.SYSTEM_ERR);
        }
    }
}
