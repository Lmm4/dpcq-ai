package org.dpcq.ai.socket.handler.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.dpcq.ai.socket.handler.dto.SendMsg;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class GameBringInParams extends SendMsg {
    /**
     * 带入数量
     */
    @NotNull
    private String chips;
    /**
     * 带入类型: gamingHandBringIn 普通带入 ;gamingMoreBringIn 带入更多 ; gamingAutoBringIn 自动带入
     */
    private String type;

    private String rate;
}
