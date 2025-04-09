package org.dpcq.ai.socket.handler;


import com.dpcq.base.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dpcq.ai.enums.Ops;
import org.dpcq.ai.service.OpsService;
import org.dpcq.ai.socket.SessionHandler;
import org.dpcq.ai.socket.handler.dto.resp.ChipLessDto;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeaveSeatHandler implements MessageHandler {
    private final OpsService opsService;

    @Override
    public String getHandlerType(String msg) {
        return Ops.CHIPS_LESS.name();
    }

    @Override
    public void handle(String userId, String msg, SessionHandler sessionHandler) {
        ChipLessDto dto = JsonUtils.parse(msg, ChipLessDto.class);
        if (dto.getUserIds().contains(Long.valueOf(userId))) {
            if (sessionHandler.getRobotInfo().isSupplement()){
                // 补充带入
                opsService.bringIn(userId, msg, sessionHandler);
            }else {
                // 离桌
                opsService.leaveSeat(userId, msg, sessionHandler);
            }
        }
    }

}
