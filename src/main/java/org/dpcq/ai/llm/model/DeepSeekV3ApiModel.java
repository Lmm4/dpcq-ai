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
public class DeepSeekV3ApiModel implements LLMStrategy {

    private final AiConfig aiConfig;
    // 密钥列表
    private final List<String> apiKeys;
    private final AtomicInteger currentKeyIndex = new AtomicInteger(0);

    public DeepSeekV3ApiModel(AiConfig aiConfig) {
        this.aiConfig = aiConfig;
        this.apiKeys = aiConfig.getApi().getDeepseek().getKey();
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
        return LLMType.DEEPSEEK;
    }

    @Override
    public String getResponse(String systemContent, String userContent) throws Exception{
//        log.info("调用DEEPSEEK-API请求内容：{},{}", systemContent, userContent);
        List<V3Message> v3Messages = List.of(new V3Message(V3Role.system.name(), systemContent), new V3Message(V3Role.user.name(), userContent));
        V3ReqParam requestMsg = new V3ReqParam(v3Messages, "deepseek-chat", false);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(aiConfig.getApi().getDeepseek().getUrl())
                .post(RequestBody.create(JsonUtils.toJsonString(requestMsg), MediaType.parse("application/json")))
                .addHeader("Authorization", "Bearer " + getNextApiKey())
                .build();
        okhttp3.Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
//        log.info("调用DEEPSEEK-API响应结果：{}", responseBody);
        V3Response parse = JsonUtils.parse(responseBody, V3Response.class);
        return MdToJsonUtil.convert(parse.getChoices().get(0).getMessage().getContent());
    }

    @Override
    public int getWeight() {
        return 8;
    }
}
