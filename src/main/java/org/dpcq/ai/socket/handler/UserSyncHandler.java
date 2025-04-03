package org.dpcq.ai.socket.handler;


import com.dpcq.base.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dpcq.ai.enums.Ops;
import org.dpcq.ai.socket.SessionHandler;
import org.dpcq.ai.socket.handler.dto.req.UserSyncParams;
import org.dpcq.ai.socket.handler.dto.resp.GameDataSyncDto;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserSyncHandler implements MessageHandler{

    @Override
    public String getHandlerType(String msg) {
        return Ops.DEV_TOOL_SELF_INFO.name();
    }

    @Override
    public void handle(String userId, String msg, SessionHandler sessionHandler) {
        GameDataSyncDto dto = JsonUtils.parse(msg, GameDataSyncDto.class);
        // 用户数据
        sessionHandler.sendMessage(JsonUtils.toJsonString(new UserSyncParams().setRobot(true).setLat(0.0).setLng(0.0).setOps(Ops.USER_SYNC)));
    }

}
