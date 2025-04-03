package org.dpcq.ai.socket;

import lombok.extern.slf4j.Slf4j;
import org.dpcq.ai.socket.handler.dto.resp.GameDataSyncDto;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class UserTableManager {

    private final Map<String, GameDataSyncDto.TableConfigVo> tableConfigMap = new ConcurrentHashMap<>();

    /**
     * 绑定机器人与牌桌
     */
    public void bindTable(String userId, GameDataSyncDto.TableConfigVo tableConfig) {
        tableConfigMap.put(userId, tableConfig);
    }

    /**
     * 获得机器人牌桌
     */
    public GameDataSyncDto.TableConfigVo getTableConfig(String userId) {
        return tableConfigMap.get(userId);
    }

    public void unbindTable(String userId) {
        tableConfigMap.remove(userId);
    }
}
