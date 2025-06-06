package org.dpcq.ai.llm.model;

import com.dpcq.base.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.dpcq.ai.config.AiConfig;
import org.dpcq.ai.enums.LLMType;
import org.dpcq.ai.enums.V3Role;
import org.dpcq.ai.llm.LLMStrategy;
import org.dpcq.ai.llm.dto.V3Message;
import org.dpcq.ai.llm.dto.V3ReqParam;
import org.dpcq.ai.llm.dto.V3Response;
import org.dpcq.ai.util.MdToJsonUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class SiliconFlowV3ApiModel implements LLMStrategy {
    private final AiConfig aiConfig;
    // 密钥列表
    private final List<String> apiKeys;
    private final AtomicInteger currentKeyIndex = new AtomicInteger(0);

    public SiliconFlowV3ApiModel(AiConfig aiConfig) {
        this.aiConfig = aiConfig;
        this.apiKeys = aiConfig.getApi().getSiliconflow().getKey();
    }
    // 轮询获取密钥
    private String getNextApiKey() {
        int index = currentKeyIndex.getAndUpdate(i -> (i + 1) % apiKeys.size());
        //        log.info("调用SILICONFLOW-API使用<{}>号密钥:{}",index,key);
        return apiKeys.get(index);
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public LLMType getType() {
        return LLMType.SILICONFLOW;
    }

    @Override
    public String getResponse(String systemContent,String userContent) {
        List<V3Message> v3Messages = List.of(new V3Message(V3Role.system.name(), systemContent), new V3Message(V3Role.user.name(), userContent));
        V3ReqParam requestMsg = new V3ReqParam(v3Messages, "deepseek-ai/DeepSeek-V3", false);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(aiConfig.getApi().getSiliconflow().getUrl())
                .post(RequestBody.create(JsonUtils.toJsonString(requestMsg), MediaType.parse("application/json")))
                .addHeader("Authorization", "Bearer " + getNextApiKey())
                .build();
        try {
            okhttp3.Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            log.info("SILICONFLOW-API响应结果：{}", responseBody);
            V3Response parse = JsonUtils.parse(responseBody, V3Response.class);
            return MdToJsonUtil.convert(parse.getChoices().get(0).getMessage().getContent());
        } catch (Exception e) {
            log.error("SILICONFLOW error", e);
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public int getWeight() {
        return 0;
    }
}
