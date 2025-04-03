package org.dpcq.ai.socket;

import lombok.extern.slf4j.Slf4j;
import org.dpcq.ai.entity.RobotEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 连接管理
 */
@Component
@Slf4j
public class WebSocketConnectionManager {

    private final WebSocketClient webSocketClient;
    private final MessageProcessor messageProcessor;
    private final Map<String, SessionHandler> sessions = new ConcurrentHashMap<>();
    @Value("${ws.service.url}")
    private String url;

    public WebSocketConnectionManager(WebSocketClient webSocketClient,
                                      MessageProcessor messageProcessor) {
        this.webSocketClient = webSocketClient;
        this.messageProcessor = messageProcessor;
    }

    public CompletableFuture<String> createConnection(RobotEntity robot, String tableId) {
        String userId = robot.getUserId().toString();
        CompletableFuture<String> future = new CompletableFuture<>();
        try {
            String s = url + userId + "/" + tableId;
            SessionHandler handler = new SessionHandler(userId, robot.getCharacters(), messageProcessor, future);
            webSocketClient.execute(handler, new WebSocketHttpHeaders(), URI.create(s));
            sessions.put(userId, handler);
        } catch (Exception e) {
            future.completeExceptionally(e);
        }

        return future;
    }

    public void sendMessage(String userId, String message) {
        SessionHandler handler = sessions.get(userId);
        if (handler != null && handler.isConnected()) {
            handler.sendMessage(message);
        }
    }

    public void closeConnection(String userId) {
        SessionHandler handler = sessions.remove(userId);
        if (handler != null) {
            handler.close();
        }
    }

    /**
     * 查询机器人是否在游戏中
     */
    public boolean isRobotInGame(String userId) {
        SessionHandler handler = sessions.get(userId);
        return handler != null && handler.isConnected();
    }


    /**
     * 定时检验连接是否断开，清楚断开的连接
     */
    @Scheduled(fixedRate = 1000 * 60)
    public void checkConnection() {
        sessions.forEach((userId, handler) -> {
            if (!handler.isConnected()) {
                log.info("用户:{} 连接已关闭", userId);
                closeConnection(userId);
            }
        });
    }

    /**
     * 保持心跳
     */
    @Scheduled(fixedRate = 1000 * 10)
    public void keepAlive() {
        sessions.forEach((userId, handler) -> {
            if (handler.isConnected()) {
                handler.sendMessage("ping");
            }
        });
    }
}
