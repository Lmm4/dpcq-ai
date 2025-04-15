package org.dpcq.ai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MsgService {
    private final KafkaProductService kafkaProductService;
    String KAFKA_TOPIC_ROBOT_LOG = "tgbot.robot_log";

    /**
     * 发送 机器人日志
     */
    @Async
    public void sendRobotAIRequestLogToTg(String time,String userId,String tableId,String content,String llmModel){
        String text = "问：\n【牌局<%s> - 用户：%s】\n 于%s调用AI大模型:<%s>，发送内容：\n [\n%s\n]";
        String msg = String.format(text,tableId,userId,time,llmModel,content);
        kafkaProductService.sendMessage(KAFKA_TOPIC_ROBOT_LOG,msg);
    }
    @Async
    public void sendRobotAIResponseLogToTg(String time,String userId,String tableId,String content,String llmModel){
        String text = "答：\n【牌局<%s> - 用户：%s】\n 于%s接受AI大模型:<%s>，接收内容：\n [\n%s\n]";

        String msg = String.format(text,tableId,userId,time,llmModel,content);
        kafkaProductService.sendMessage(KAFKA_TOPIC_ROBOT_LOG,msg);
    }

    @Async
    public void sendRobotAITimeOutLogToTg(String time,String userId,String tableId,String llmModel){
        String text = "超时：\n【牌局<%s> - 用户：%s】\n 于%s调用AI大模型:<%s> 响应超时";
        String msg = String.format(text,tableId,userId,time,llmModel);
        kafkaProductService.sendMessage(KAFKA_TOPIC_ROBOT_LOG,msg);
    }

    @Async
    public void sendRobotAIErrorLogToTg(String time,String userId,String tableId,String llmModel){
        String text = "异常：\n【牌局<%s> - 用户：%s】\n 于%s调用AI大模型:<%s> 异常";
        String msg = String.format(text,tableId,userId,time,llmModel);
        kafkaProductService.sendMessage(KAFKA_TOPIC_ROBOT_LOG,msg);
    }


}
