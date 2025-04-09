package org.dpcq.ai.service;

import com.dpcq.base.enums.SymbolEnum;
import com.dpcq.base.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dpcq.ai.enums.Ops;
import org.dpcq.ai.socket.SessionHandler;
import org.dpcq.ai.socket.UserTableManager;
import org.dpcq.ai.socket.handler.dto.req.GameBringInParams;
import org.dpcq.ai.socket.handler.dto.req.LeaveSeatParams;
import org.dpcq.ai.socket.handler.dto.resp.GameDataSyncDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpsService {
    private final UserTableManager userTableManager;

    /**
     * 离桌
     */
    public void leaveSeat(String userId, String msg, SessionHandler sessionHandler) {
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
    public void bringIn(String userId, String msg, SessionHandler sessionHandler) {
        GameDataSyncDto.TableConfigVo tableConfig = userTableManager.getTableConfig(userId);
        GameBringInParams params = new GameBringInParams();
        params.setOps(Ops.GAME_BRING_IN);
        params.setChips(SymbolEnum.bizToFront(tableConfig.getMinBring(), tableConfig.getSymbol()).toPlainString());
        params.setType("gamingHandBringIn");
        sessionHandler.sendMessage(JsonUtils.toJsonString(params));
    }

    /**
     * 下一手离座
     */
    public void leaveSeatNext(String userId, SessionHandler sessionHandler){
        LeaveSeatParams params = new LeaveSeatParams();
        params.setOps(Ops.NEXT_ROUND_LEAVE_SEAT);
        params.setUserId(Long.valueOf(userId));
        sessionHandler.sendMessage(JsonUtils.toJsonString(params));
    }

}
