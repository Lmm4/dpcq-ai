package org.dpcq.ai.task;

import com.dpcq.base.enums.SymbolEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dpcq.ai.entity.RobotEntity;
import org.dpcq.ai.rpc.FeignWalletApi;
import org.dpcq.ai.rpc.dto.UserBalanceVo;
import org.dpcq.ai.rpc.dto.WalletMsg;
import org.dpcq.ai.rpc.dto.WalletResponse;
import org.dpcq.ai.service.RobotService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * 机器人补充筹码任务
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SupplementTask {

    private final RobotService robotService;
    private final FeignWalletApi feignWalletApi;
    // 余额标准
    private final BigDecimal balanceStandard = new BigDecimal(2000);

//    @Scheduled(fixedDelay = 1000 * 60 * 2)
    public void supplement() {
        log.info("=====================开始补充筹码=======================");
        List<Long> userIds = robotService.getFreeRobotList().stream().map(RobotEntity::getUserId).toList();
        if (userIds.isEmpty()) {
            return;
        }
        List<UserBalanceVo> userBalanceVos = feignWalletApi.balanceList(userIds, SymbolEnum.DPCQ.getSymbol());
        userBalanceVos.forEach(userBalanceVo -> {
            BigDecimal balance = new BigDecimal(userBalanceVo.getBalance());
            if (balance.compareTo(balanceStandard) < 0) {
                WalletResponse response = feignWalletApi.balanceChange(WalletMsg.builder()
                        .userId(userBalanceVo.getUserId())
                        .amount(SymbolEnum.convertLong(balanceStandard.subtract(balance), SymbolEnum.DPCQ.getSymbol()))
                        .symbol(SymbolEnum.DPCQ.getSymbol())
                        .relationId(userBalanceVo.getUserId())
                        .type("adminAdd")
                        .build());
                if (response.getSuccess()){
                    log.info("【补充筹码成功】机器人：{}，补充：{}", userBalanceVo.getUserId(), balanceStandard.subtract(balance));
                }
            }
        });

    }
}
