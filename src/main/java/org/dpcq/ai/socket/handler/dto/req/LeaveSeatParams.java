package org.dpcq.ai.socket.handler.dto.req;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.dpcq.ai.socket.handler.dto.SendMsg;
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class LeaveSeatParams extends SendMsg {
    private Long userId;
}
