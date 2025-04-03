package org.dpcq.ai.socket.handler.dto.req;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.dpcq.ai.socket.handler.dto.SendMsg;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class UserSyncParams extends SendMsg {

    /**
     * 精度
     */
    private Double lng;

    /**
     * 纬度
     */
    private Double lat;

    /**
     * 机器人
     */
    private Boolean robot;
}
