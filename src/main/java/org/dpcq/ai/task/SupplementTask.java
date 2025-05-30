package org.dpcq.ai.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dpcq.ai.entity.RobotEntity;
import org.dpcq.ai.service.RobotService;
import org.dpcq.ai.service.WalletService;
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
    // 余额标准
    private final BigDecimal balanceStandard = new BigDecimal(1000);
    private final WalletService walletService;
    @Scheduled(initialDelay = 1000 * 60 , fixedDelay = 1000 * 60 * 2)
    public void supplement() {
        List<Long> userIds = robotService.getFreeRobotList().stream().map(RobotEntity::getUserId).toList();
        if (userIds.isEmpty()) {
            return;
        }
        userIds.forEach(userId -> {
            BigDecimal balance = walletService.get(String.valueOf(userId));
            if (balance.compareTo(balanceStandard) < 0) {
                walletService.set(String.valueOf(userId), "10000");
                log.info("【补充筹码成功】机器人：{}，补充：{}", userId, balanceStandard.subtract(balance));
            }
        });

    }
}
