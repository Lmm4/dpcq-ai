package org.dpcq.ai.socket.handler.dto.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dpcq.ai.socket.handler.dto.Notify;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeaveSeatDto extends Notify {
    private long userId;

    private String avatar;

    /**
     * 座位ID
     */
    private int seatId;

    private String cancelWatchType;

    private List<Long> waitUserIds;

    /**
     * 逃跑
     */
    private boolean squidRun;

    private long resetClearRoundTime;

}
