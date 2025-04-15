package org.dpcq.ai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dpcq.ai.config.SendLogConfig;
import org.dpcq.ai.enums.GameProcessEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProductService {
    private final SendLogConfig sendLogConfig;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String topic,String message) {
        if (sendLogConfig.isSendLog()){
            String message1 = GameProcessEvent.translateFormat(message);
            this.kafkaTemplate.send(topic,message1);
//            log.info("TOPIC:{}发送消息:{}",topic,message1);
        }
    }
}
