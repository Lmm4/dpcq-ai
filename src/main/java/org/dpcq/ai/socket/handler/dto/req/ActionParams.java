package org.dpcq.ai.socket.handler.dto.req;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.dpcq.ai.socket.handler.dto.SendMsg;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class ActionParams extends SendMsg {

    private Long userId;
    /**
     * 购买的筹码量
     */
    private Long chips;

    /**
     * 操作凭证
     */
    private long timeout;

    /**
     * 挂机自动操作操作
     */
    private boolean userOps;

}
