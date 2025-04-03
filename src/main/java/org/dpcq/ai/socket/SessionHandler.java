package org.dpcq.ai.socket;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
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
    private final String userId;
    private final Integer userCharactor;
    private final MessageProcessor messageProcessor;
    private final CompletableFuture<String> connectionFuture;
    private WebSocketSession session;

    public SessionHandler(String userId,
                          Integer userCharactor,
                          MessageProcessor messageProcessor,
                          CompletableFuture<String> connectionFuture) {
        this.userId = userId;
        this.userCharactor = userCharactor;
        this.messageProcessor = messageProcessor;
        this.connectionFuture = connectionFuture;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        this.session = session;
        connectionFuture.complete(userId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        log.info("{}接受消息：{}",userId,message.getPayload());
        messageProcessor.process(userId, message.getPayload(),this);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        connectionFuture.completeExceptionally(exception);
    }

    public void sendMessage(String message) {
        if (isConnected()) {
            try {
                log.info("{}发送消息：{}",userId,message);
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
