package org.dpcq.ai.socket.handler.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RobotInfo {
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 风格ID
     */
    private Integer characterId;
    /**
     * 筹码不足时是否补充
     */
    private boolean supplement;
    /**
     * 牌桌ID
     */
    private String tableId;
}
