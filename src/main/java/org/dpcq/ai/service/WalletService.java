package org.dpcq.ai.service;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class WalletService {

    private final StringRedisTemplate  redisTemplate;
    public BigDecimal get(String userId) {
        var balance = redisTemplate.opsForValue().get(redisUserWalletKey(userId));
        return balance == null ? BigDecimal.ZERO : new BigDecimal(StrUtil.unWrap(balance,'"'));
    }

    public void set(String userId, String amount) {
        redisTemplate.opsForValue().set(redisUserWalletKey(userId), amount);
    }

    private String redisUserWalletKey(String userId) {
        return "externalWallet:" + userId;
    }

}
