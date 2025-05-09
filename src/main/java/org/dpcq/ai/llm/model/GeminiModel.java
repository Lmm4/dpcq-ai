package org.dpcq.ai.llm.model;


import com.dpcq.base.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.dpcq.ai.config.AiConfig;
import org.dpcq.ai.enums.LLMType;
import org.dpcq.ai.llm.LLMStrategy;
import org.dpcq.ai.llm.dto.GeminiReqParam;
import org.dpcq.ai.llm.dto.GeminiResponse;
import org.dpcq.ai.util.MdToJsonUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class GeminiModel implements LLMStrategy {
    private final AiConfig aiConfig;
    // 密钥列表
    private final List<String> apiKeys;
    private final AtomicInteger currentKeyIndex = new AtomicInteger(0);

    public GeminiModel(AiConfig aiConfig) {
        this.aiConfig = aiConfig;
        this.apiKeys = aiConfig.getApi().getGemini().getKey();
    }

    // 轮询获取密钥
    private String getNextApiKey() {
        int index = currentKeyIndex.getAndUpdate(i -> (i + 1) % apiKeys.size());
        return apiKeys.get(index);
    }


    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public LLMType getType() {
        return LLMType.GEMINI;
    }

    @Override
    public String getResponse(String systemContent, String userContent) {
        GeminiReqParam param = GeminiReqParam.getParam(systemContent + userContent);
        String url = aiConfig.getApi().getGemini().getUrl() + getNextApiKey();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JsonUtils.toJsonString(param), MediaType.parse("application/json")))
                .build();
        try {
            okhttp3.Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
//            log.info("GEMINI-API响应结果：{}", responseBody);
            GeminiResponse parse = JsonUtils.parse(responseBody, GeminiResponse.class);
            return MdToJsonUtil.convert(parse.getCandidates().get(0).getContent().getParts().get(0).getText());
        } catch (Exception e) {
            log.error("GEMINI error", e);
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public int getWeight() {
        return 2;
    }
}
