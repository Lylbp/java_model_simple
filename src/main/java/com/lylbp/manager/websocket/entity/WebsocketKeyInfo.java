package com.lylbp.manager.websocket.entity;

import com.lylbp.manager.websocket.enums.PlatformEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 连接用户对象
 *
 * @Author weiwenbin
 * @Date: 2020/9/21 下午4:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebsocketKeyInfo {
    /**
     * 所属平台
     */
    private PlatformEnum platformEnum;

    /**
     * 连接时间
     */
    private Date connectDate;

    /**
     * 用户唯一表识识别
     */
    private String userFlag;

    /**
     * 接受的类型
     */
    private List<String> pushTypeList;
}
