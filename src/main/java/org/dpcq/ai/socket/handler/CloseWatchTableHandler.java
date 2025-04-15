package org.dpcq.ai.socket.handler;


import com.dpcq.base.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dpcq.ai.enums.Ops;
import org.dpcq.ai.service.OpsService;
import org.dpcq.ai.socket.SessionHandler;
import org.dpcq.ai.socket.handler.dto.resp.LeaveSeatDto;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CloseWatchTableHandler implements MessageHandler {
    private final OpsService opsService;

    @Override
    public String getHandlerType(String msg) {
        return Ops.LEAVE_SEAT.name();
    }

    @Override
    public void handle(String userId, String msg, SessionHandler sessionHandler) {
        LeaveSeatDto dto = JsonUtils.parse(msg, LeaveSeatDto.class);
        if (userId.equals(String.valueOf(dto.getUserId()))) {
            opsService.cancelWatchTable(userId, sessionHandler);
        }
    }

}
