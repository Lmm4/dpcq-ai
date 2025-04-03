package org.dpcq.ai.socket.handler.dto.req;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dpcq.ai.socket.handler.dto.SendMsg;


@EqualsAndHashCode(callSuper = true)
@Data
public class TakeSeatParams extends SendMsg {

    private Integer seatId;
}
