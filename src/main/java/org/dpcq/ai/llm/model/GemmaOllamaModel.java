package org.dpcq.ai.llm.model;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dpcq.ai.enums.LLMType;
import org.dpcq.ai.llm.LLMStrategy;
import org.dpcq.ai.util.MdToJsonUtil;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Component;
@Component
@Slf4j
@RequiredArgsConstructor
public class GemmaOllamaModel implements LLMStrategy {
    private final OllamaChatModel ollamaChatModel;

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public LLMType getType() {
        return LLMType.GEMMA;
    }

    @Override
    public String getResponse(String systemContent, String userContent) {
        ChatResponse chatResponse = ollamaChatModel.call(
                new Prompt(userContent, OllamaOptions.builder()
                        .model("gemma3:12b")//指定使用哪个大模型
                        .temperature(0.8).build()));
        return MdToJsonUtil.convert(chatResponse.getResult().getOutput().getText());
    }

    @Override
    public int getWeight() {
        return 0;
    }
}
