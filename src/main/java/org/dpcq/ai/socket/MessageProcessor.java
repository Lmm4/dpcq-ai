package org.dpcq.ai.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dpcq.ai.socket.handler.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息处理器
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MessageProcessor {
    private final Map<String, MessageHandler> handlers = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;

    @Autowired
    public MessageProcessor(List<MessageHandler> handlerList, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        handlerList.forEach(handler ->
                handlers.put(handler.getHandlerType("ops"), handler));
    }

    public void process(String connectionId, String message, SessionHandler sessionHandler) {
        if (message.equals("pong")){
            return;
        }
        if (message.contains("joinTableSuccess")){
            return;
        }
        try {
            JsonNode rootNode = objectMapper.readTree(message);
            String messageType = rootNode.get("ops").asText();

            MessageHandler handler = handlers.get(messageType);
            if (handler != null) {
                handler.handle(connectionId, message, sessionHandler);
            } else {
                handleUnknownType(connectionId, messageType,message);
            }
        } catch (JsonProcessingException e) {
            handleParseError(connectionId, message, e);
        }
    }

    private void handleUnknownType(String connectionId, String type ,String message) {
        // 未实现处理的消息类型
        log.debug("未实现处理的消息类型：{},MSG:{}",type,message);
    }


    private void handleParseError(String connectionId, String message, Exception e) {
        log.error("消息解析失败：{}",message,e);
        e.printStackTrace();
    }
}

