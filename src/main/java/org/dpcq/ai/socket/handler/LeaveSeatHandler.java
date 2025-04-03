package org.dpcq.ai.socket.handler;


import com.dpcq.base.enums.SymbolEnum;
import com.dpcq.base.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dpcq.ai.enums.Ops;
import org.dpcq.ai.socket.SessionHandler;
import org.dpcq.ai.socket.UserTableManager;
import org.dpcq.ai.socket.handler.dto.req.GameBringInParams;
import org.dpcq.ai.socket.handler.dto.req.LeaveSeatParams;
import org.dpcq.ai.socket.handler.dto.resp.ChipLessDto;
import org.dpcq.ai.socket.handler.dto.resp.GameDataSyncDto;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeaveSeatHandler implements MessageHandler {
    private final UserTableManager userTableManager;

    @Override
    public String getHandlerType(String msg) {
        return Ops.CHIPS_LESS.name();
    }

    @Override
    public void handle(String userId, String msg, SessionHandler sessionHandler) {
        ChipLessDto dto = JsonUtils.parse(msg, ChipLessDto.class);
        if (dto.getUserIds().contains(Long.valueOf(userId))){
            // 离桌
//            leaveSeat(userId, msg, sessionHandler);
            // 补充带入
            bringIn(userId, msg, sessionHandler);

        }
    }

    /**
     * 离桌
     */
    private void leaveSeat(String userId, String msg, SessionHandler sessionHandler) {
            LeaveSeatParams params = new LeaveSeatParams();
            params.setOps(Ops.LEAVE_SEAT);
            params.setUserId(Long.valueOf(userId));
            sessionHandler.sendMessage(JsonUtils.toJsonString(params));
            params.setOps(Ops.CANCEL_WATCH_TABLE);
            sessionHandler.sendMessage(JsonUtils.toJsonString(params));
            sessionHandler.close();
    }

    /**
     * 带入
     */
    private void bringIn(String userId, String msg, SessionHandler sessionHandler) {
        GameDataSyncDto.TableConfigVo tableConfig = userTableManager.getTableConfig(userId);
        GameBringInParams params = new GameBringInParams();
        params.setOps(Ops.GAME_BRING_IN);
        params.setChips(SymbolEnum.bizToFront(tableConfig.getMinBring(), tableConfig.getSymbol()).toPlainString());
        params.setType("gamingHandBringIn");
        sessionHandler.sendMessage(JsonUtils.toJsonString(params));
    }

}
