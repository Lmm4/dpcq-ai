package org.dpcq.ai.socket;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.dpcq.ai.socket.handler.dto.RobotInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * 会话管理
 */
@Slf4j
@Getter
public class SessionHandler extends TextWebSocketHandler {
    private final RobotInfo robotInfo;
    private final MessageProcessor messageProcessor;
    private final CompletableFuture<String> connectionFuture;
    private WebSocketSession session;

    public SessionHandler(RobotInfo robotInfo,
                          MessageProcessor messageProcessor,
                          CompletableFuture<String> connectionFuture) {
        this.robotInfo = robotInfo;
        this.messageProcessor = messageProcessor;
        this.connectionFuture = connectionFuture;
    }

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) {
        this.session = session;
        connectionFuture.complete(robotInfo.getUserId());
    }

    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session, TextMessage message) {
        log.debug("{}接受消息：{}",robotInfo.getUserId(),message.getPayload());
        messageProcessor.process(robotInfo.getUserId(), message.getPayload(),this);
    }

    @Override
    public void handleTransportError(@NotNull WebSocketSession session, Throwable exception) {
        connectionFuture.completeExceptionally(exception);
    }

    public void sendMessage(String message) {
        if (isConnected()) {
            try {
                log.debug("{}发送消息：{}",robotInfo.getUserId(),message);
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                log.error("发送消息失败：{}",message);
                throw new RuntimeException("Message sending failed", e);
            }
        }
    }

    public void close() {
        try {
            if (session != null && session.isOpen()) {
                session.close();
            }
        } catch (IOException e) {
            // Log close error
        }
    }

    public boolean isConnected() {
        return session != null && session.isOpen();
    }
}
