package com.lylbp.manager.websocket.entity;

import com.lylbp.manager.websocket.enums.PlatformEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author weiwenbin
 * @date 2020/12/17 下午3:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessSendUserInfo {
    /**
     * 用户唯一表识识别
     */
    private String userFlag;

    /**
     * 所属平台
     */
    private PlatformEnum platformEnum;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SuccessSendUserInfo that = (SuccessSendUserInfo) o;
        return Objects.equals(getUserFlag(), that.getUserFlag()) && Objects.equals(getPlatformEnum(), that.getPlatformEnum());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserFlag());
    }
}
