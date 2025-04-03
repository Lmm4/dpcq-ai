package org.dpcq.ai.llm.model;

import com.dpcq.base.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@Component
@Slf4j
public class SiliconFlowV3ApiModel implements LLMStrategy {
    private final AiConfig aiConfig;

    @Override
    public boolean isEnabled() {
        return false;
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
                .url(aiConfig.getSiliconflowUrl())
                .post(RequestBody.create(JsonUtils.toJsonString(requestMsg), MediaType.parse("application/json")))
                .addHeader("Authorization", aiConfig.getSiliconflowKey())
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
}
