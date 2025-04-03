package org.dpcq.ai.config;

import org.dpcq.ai.socket.MessageProcessor;
import org.dpcq.ai.socket.WebSocketConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@Configuration
public class WebSocketConfig {

    @Bean
    public WebSocketClient webSocketClient() {
        return new StandardWebSocketClient();
    }

    @Bean
    public WebSocketConnectionManager webSocketConnectionManager(
            WebSocketClient webSocketClient,
            MessageProcessor messageProcessor) {
        return new WebSocketConnectionManager(webSocketClient, messageProcessor);
    }
}
