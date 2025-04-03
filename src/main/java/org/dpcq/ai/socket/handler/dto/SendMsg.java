package org.dpcq.ai.socket.handler.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.dpcq.ai.enums.Ops;

@Data
@Accessors(chain = true)
public class SendMsg {

    /**
     * 操作 ops
     */
    private Ops ops;
}
