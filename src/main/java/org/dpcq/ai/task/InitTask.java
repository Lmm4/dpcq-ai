package org.dpcq.ai.task;

import com.dpcq.base.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dpcq.ai.pojo.Constants;
import org.dpcq.ai.socket.WebSocketConnectionManager;
import org.dpcq.ai.socket.handler.dto.RobotInfo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class InitTask implements CommandLineRunner {
    private final WebSocketConnectionManager webSocketConnectionManager;
    private final StringRedisTemplate redisTemplate;

    @Override
    public void run(String... args) throws Exception {
        log.info("init task start");
        Set<String> keys = redisTemplate.keys(String.format(Constants.ROBOT_ONLINE_KEY, "*"));
        if (!keys.isEmpty()) {
            keys.forEach(key -> {
                String userId = key.substring(key.lastIndexOf(":") + 1);
                String info = redisTemplate.opsForValue().get(key);
                RobotInfo robotInfo = JsonUtils.parse(info, RobotInfo.class);
                webSocketConnectionManager.createConnection(robotInfo);
                log.info("机器人重连{}加入牌桌{}", userId, robotInfo.getTableId());
            });
        }
        log.info("init task end");
    }
}
