package org.dpcq.ai.socket.handler;


import com.dpcq.base.enums.SymbolEnum;
import com.dpcq.base.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dpcq.ai.enums.Ops;
import org.dpcq.ai.pojo.Constants;
import org.dpcq.ai.rpc.FeignWalletApi;
import org.dpcq.ai.service.OpsService;
import org.dpcq.ai.socket.SessionHandler;
import org.dpcq.ai.socket.UserTableManager;
import org.dpcq.ai.socket.handler.dto.SendMsg;
import org.dpcq.ai.socket.handler.dto.resp.GameDataSyncDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskSeatHandler implements MessageHandler{

    private final UserTableManager userTableManager;
    private final FeignWalletApi feignWalletApi;
    private final OpsService opsService;
    private final StringRedisTemplate redisTemplate;
    @Value("${spring.profiles.active}")
    private String profile;
    @Override
    public String getHandlerType(String msg) {
        return Ops.TABLE_DATA_SYNC.name();
    }

    @Override
    public void handle(String userId, String msg, SessionHandler sessionHandler) {
        GameDataSyncDto dto = JsonUtils.parse(msg, GameDataSyncDto.class);
        // 在座上则跳过
        if (dto.getSeats().stream().anyMatch(seat -> Objects.nonNull(seat.getPlayer()) && seat.getPlayer().getId().toString().equals(userId))) {
            log.info("{}已在座位上",userId);
            return;
        }
        // 没有空位跳过
        if (dto.getSeats().stream().noneMatch(seat -> Objects.isNull(seat.getPlayer()))) {
            log.info("{}座位已满",dto.getTableConfig().getId().toString());
            return;
        }
        // 查询余额是否满足最低带入
        if (!"local".equals(profile)){
            Long balance = feignWalletApi.getBalance(Long.valueOf(userId), SymbolEnum.DPCQ.getSymbol(), "USER");
            if (dto.getTableConfig().getMinBring() > SymbolEnum.dpToBizDecimal(balance, SymbolEnum.DPCQ.getSymbol())){
                log.info("{}余额不足，无法入座",userId);
                opsService.cancelWatchTable(userId, sessionHandler);
                return;
            }
        }
        // 未在座位上，执行入座
        sessionHandler.sendMessage(JsonUtils.toJsonString(new SendMsg().setOps(Ops.ROBOT_TAKE_SEAT)));
        // 绑定牌桌
        userTableManager.bindTable(userId, dto.getTableConfig());
        // 缓存机器人链接状态
        redisTemplate.opsForValue().set(String.format(Constants.ROBOT_ONLINE_KEY, userId), JsonUtils.toJsonString(sessionHandler.getRobotInfo()));
    }

}
