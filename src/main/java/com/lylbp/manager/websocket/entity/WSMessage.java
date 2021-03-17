package com.lylbp.manager.websocket.entity;

import com.lylbp.manager.websocket.enums.PlatformEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * WSMessage对象
 *
 * @author weiwenbin
 * @date 2020/7/20 下午3:54
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "WSMessage对象", description = "websocket信息实体")
@NoArgsConstructor
@AllArgsConstructor
public class WSMessage {
    public WSMessage(String from, String fromUser, String toUser, String pushType, String message, List<PlatformEnum> platformEnums) {
        this.from = from;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.pushType = pushType;
        this.message = message;
        this.platformEnums = platformEnums;
    }

    @ApiModelProperty("来源 ping:来源于后台服务器 pong:来源于前端")
    private String from;

    @ApiModelProperty("来源用户")
    private String fromUser;

    @ApiModelProperty("接受人 ALL为全部")
    private String toUser;

    @ApiModelProperty("推送类型")
    private String pushType;

    @ApiModelProperty("实际信息")
    private String message;

    @ApiModelProperty("要发送的平台")
    private List<PlatformEnum> platformEnums;

    @ApiModelProperty("唤醒地址")
    private String convertAddress;

    @ApiModelProperty("是否发送成功")
    private Boolean isSuccess = false;

    @ApiModelProperty("发送成功的用户信息")
    private List<SuccessSendUserInfo> sendUserInfoList = new ArrayList<>(10);
}
