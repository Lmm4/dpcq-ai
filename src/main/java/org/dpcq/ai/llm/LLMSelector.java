package org.dpcq.ai.llm;

import cn.hutool.core.date.DateUtil;
import lombok.RequiredArgsConstructor;
import org.dpcq.ai.llm.dto.TableData;
import org.dpcq.ai.service.MsgService;
import org.springframework.stereotype.Component;

import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 大模型选择器
 */
@Component
@RequiredArgsConstructor
public class LLMSelector {
    private final MsgService msgService;
    private final List<LLMStrategy> strategies;
    Random random = new Random();

    /**
     * 选择调用的模型，按状态和权重
     */
    public LLMStrategy selectStrategy() {
        List<LLMStrategy> enabledStrategies = strategies.stream()
                .filter(s -> s.isEnabled() && s.getWeight() > 0)
                .toList();

        if (enabledStrategies.isEmpty()) {
            throw new RuntimeException("没有可用的LLM服务");
        }
        // 总权重
        int totalWeight = enabledStrategies.stream()
                .mapToInt(LLMStrategy::getWeight)
                .sum();
        // 随机权重
        int randomWeight = random.nextInt(totalWeight);
        int currentSum = 0;

        for (LLMStrategy strategy : enabledStrategies) {
            currentSum += strategy.getWeight();
            if (randomWeight < currentSum) {
                return strategy;
            }
        }

        return enabledStrategies.get(enabledStrategies.size() - 1);
    }

    public String callAI(TableData data){
        String systemContent = PromptGenerator.getSystemContent(data.getCharacterId());
        String userContent =PromptGenerator.getUserContent(data);
        LLMStrategy llmStrategy = selectStrategy();
        msgService.sendRobotAIRequestLogToTg(DateUtil.formatTime(new Date()),data.getUserId(),data.getTableId().toString(),systemContent+userContent,llmStrategy.getType().name());
        String response = "";
        try {
            response = llmStrategy.getResponse(systemContent, userContent);
        }catch (SocketTimeoutException e){
            msgService.sendRobotAITimeOutLogToTg(DateUtil.formatTime(new Date()),data.getUserId(),data.getTableId().toString(),llmStrategy.getType().name());
            return response;
        }catch (Exception e){
            msgService.sendRobotAIErrorLogToTg(DateUtil.formatTime(new Date()),data.getUserId(),data.getTableId().toString(),llmStrategy.getType().name());
            return response;
        }
        msgService.sendRobotAIResponseLogToTg(DateUtil.formatTime(new Date()),data.getUserId(),data.getTableId().toString(),response,llmStrategy.getType().name());
        return response;
    }
}
