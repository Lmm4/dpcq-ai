package org.dpcq.ai.socket.handler;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dpcq.ai.enums.Ops;
import org.dpcq.ai.socket.SessionHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BringInHandler implements MessageHandler {

    @Override
    public String getHandlerType(String msg) {
        return Ops.TAKE_SEAT.name();
    }

    @Override
    public void handle(String userId, String msg, SessionHandler sessionHandler) {
//        // 未在座位上，执行入座
//        sessionHandler.sendMessage(JsonUtils.toJsonString(new GameBringInDto()
//                .setType("gamingHandBringIn")
//                .setChips("0.1")
//                .setOps(Ops.GAME_BRING_IN)));
    }

}
