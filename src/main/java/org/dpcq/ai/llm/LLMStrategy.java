package org.dpcq.ai.llm;

import org.dpcq.ai.enums.LLMType;

public interface LLMStrategy {
    boolean isEnabled();      // 是否启用该服务
    LLMType getType();         // 获取服务类型
    String getResponse(String systemContent, String userContent);
}
