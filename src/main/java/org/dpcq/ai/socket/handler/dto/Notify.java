package org.dpcq.ai.socket.handler.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dpcq.ai.enums.Ops;

/**
 * 牌桌通知
 */
@Getter
@Setter
@NoArgsConstructor
public class Notify {

    /**
     * 触发操作
     */
    private String ops;

    /**
     * 错误 code
     */
    private int code;

    /**
     * 数据版本号
     */
    private int version;

    public Notify(Ops ops) {
        this.ops = ops.name();
    }
}
