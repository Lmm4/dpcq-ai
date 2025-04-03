package org.dpcq.ai.llm;

import lombok.RequiredArgsConstructor;
import org.dpcq.ai.llm.dto.TableData;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

/**
 * 大模型选择器
 */
@Component
@RequiredArgsConstructor
public class LLMSelector {

    private final List<LLMStrategy> strategies;
    Random random = new Random();

    public LLMStrategy selectStrategy() {
        List<LLMStrategy> enabledStrategies = strategies.stream()
                .filter(LLMStrategy::isEnabled)
                .toList();

        if (enabledStrategies.isEmpty()) {
            throw new RuntimeException("没有可用的LLM服务");
        }
        int index = random.nextInt(enabledStrategies.size());
        return enabledStrategies.get(index);
    }

    public String callAI(TableData data){
        return selectStrategy().getResponse(PromptGenerator.getSystemContent(data.getCharacterId()),PromptGenerator.getUserContent(data));
    }
}
