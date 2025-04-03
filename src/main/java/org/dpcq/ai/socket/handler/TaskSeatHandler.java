package org.dpcq.ai.socket.handler;


import com.dpcq.base.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dpcq.ai.enums.Ops;
import org.dpcq.ai.socket.SessionHandler;
import org.dpcq.ai.socket.UserTableManager;
import org.dpcq.ai.socket.handler.dto.SendMsg;
import org.dpcq.ai.socket.handler.dto.resp.GameDataSyncDto;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskSeatHandler implements MessageHandler{

    private final UserTableManager userTableManager;
    @Override
    public String getHandlerType(String msg) {
        return Ops.TABLE_DATA_SYNC.name();
    }

    @Override
    public void handle(String userId, String msg, SessionHandler sessionHandler) {
        GameDataSyncDto dto = JsonUtils.parse(msg, GameDataSyncDto.class);
        // 在座上则跳过
        if (dto.getSeats().stream().noneMatch(seat -> seat.getPlayer() == null) &&
            dto.getSeats().stream().anyMatch(seat -> seat.getPlayer().getId().toString().equals(userId))) {
            log.info("{}已在座位上",userId);
            return;
        }
        // 未在座位上，执行入座
        sessionHandler.sendMessage(JsonUtils.toJsonString(new SendMsg().setOps(Ops.ROBOT_TAKE_SEAT)));
        // 绑定牌桌
        userTableManager.bindTable(userId, dto.getTableConfig());
    }

}
