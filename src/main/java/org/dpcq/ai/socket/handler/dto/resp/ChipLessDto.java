package org.dpcq.ai.socket.handler.dto.resp;

import lombok.Data;
import org.dpcq.ai.socket.handler.dto.Notify;

import java.util.List;

@Data
public class ChipLessDto extends Notify {
    private Long bringInTimeout;
    private List<Long> userIds;
}
