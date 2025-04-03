package org.dpcq.ai.socket.handler;

import com.dpcq.base.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dpcq.ai.enums.Ops;
import org.dpcq.ai.enums.Stage;
import org.dpcq.ai.llm.LLMSelector;
import org.dpcq.ai.llm.dto.TableData;
import org.dpcq.ai.llm.dto.V3Response;
import org.dpcq.ai.socket.SessionHandler;
import org.dpcq.ai.socket.handler.dto.req.ActionParams;
import org.dpcq.ai.util.MdToJsonUtil;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

@Component
@Slf4j
@RequiredArgsConstructor
public class RobotNotifyHandler implements MessageHandler{
    private final ObjectMapper objectMapper;
    private final LLMSelector llmSelector;
    @Override
    public String getHandlerType(String ops) {
        return Ops.ROBOT_DATA.name();
    }

    @Override
    public void handle(String userId, String msg, SessionHandler sessionHandler) {
        try {
            TableData data = JsonUtils.parse(msg, TableData.class);
            data.setCharacterId(sessionHandler.getUserCharactor());
            String content = llmSelector.callAI(data);
            log.info("大模型响应数据：{}", content);
            ActionParams action = dealJson(content,data);
            log.info("决策结果：{}", action);

            action.setUserOps(true);
            action.setTimeout(data.getTimeout());
            sessionHandler.sendMessage(JsonUtils.toJsonString(action));
        }catch (Exception e){
            log.error("解析机器人数据报错");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String content = "{\"id\":\"1147f52f-4571-4c72-b31a-a8c318e0b95a\",\"object\":\"chat.completion\",\"created\":1743493222,\"model\":\"deepseek-chat\",\"choices\":[{\"index\":0,\"message\":{\"role\":\"assistant\",\"content\":\"```json\\n{\\\"call\\\":\\\"30\\\",\\\"fold\\\":\\\"70\\\",\\\"amount\\\":\\\"\\\",\\\"raise\\\":\\\"\\\",\\\"all_in\\\":\\\"\\\"}\\n```\"},\"logprobs\":null,\"finish_reason\":\"stop\"}],\"usage\":{\"prompt_tokens\":233,\"completion_tokens\":24,\"total_tokens\":257,\"prompt_tokens_details\":{\"cached_tokens\":0},\"prompt_cache_hit_tokens\":0,\"prompt_cache_miss_tokens\":233},\"system_fingerprint\":\"fp_3d5141a69a_prod0225\"}";
        V3Response parse = JsonUtils.parse(content, V3Response.class);
        String json = MdToJsonUtil.convert(parse.getChoices().get(0).getMessage().getContent());
        String json1 = "{\n" +
                "\"call\":\"0%\",\n" +
                "\"fold\":\"100%\",\n" +
                "\"amount\":\"\",\n" +
                "\"raise\":\"0%\",\n" +
                "\"all_in\":\"0%\"\n" +
                "}";
        TableData data = new TableData();
        data.setStage(Stage.PRE_FLOP.getStage());
        dealJson(json1,data);
    }

    /**
     * 处理大模型响应数据
     */
    private static ActionParams dealJson(String json, TableData data){
        try {
            ObjectNode rootNode = (ObjectNode) new ObjectMapper().readTree(json);
            // 通过操作权重计算结果
            long raiseRateValue = 0;
            // 移除 amount字段
            if (rootNode.has("amount")) {
                raiseRateValue = rootNode.get("amount").asLong();
                rootNode.remove("amount");
            }
            // 是否有加注可能
            if (raiseRateValue == 0){
                rootNode.remove("raise");
            }
            // 翻牌前弃牌占比调整
            if (data.getStage().equals(Stage.PRE_FLOP.getStage())){
                if (rootNode.has("fold")){
                    String text = rootNode.get("fold").asText();
                    double fold = text.contains("%") ? Double.parseDouble(text.replace("%", "")) : Double.parseDouble(text);
                    if (fold <= 70){
                        rootNode.remove("fold");
                    }
                }
            }
            Map<String, Double> fields = new LinkedHashMap<>();
            rootNode.fields().forEachRemaining(entry -> {
                JsonNode valueNode = entry.getValue();
                if (valueNode.asText().contains("%")){
                    String text = valueNode.asText();
                    Double value = Double.valueOf(text.replace("%", ""));
                    fields.put(entry.getKey(), value);
                }else {
                    fields.put(entry.getKey(), valueNode.asDouble());
                }
            });
            double total = fields.values().stream().mapToDouble(Double::doubleValue).sum();
            if (total <= 0) {
                throw new IllegalArgumentException("字段值的总和必须大于0");
            }
            double randomValue = new Random().nextDouble() * total;
            double cumulative = 0;
            String op = "";
            for (Map.Entry<String, Double> entry : fields.entrySet()) {
                cumulative += entry.getValue();
                if (randomValue <= cumulative) {
                    op = entry.getKey();
                    break;
                }
            }
            ActionParams action = new ActionParams();
            switch (op) {
                case "fold":
                    action.setOps(Ops.FOLD);
                    action.setChips(null);
                    break;
                case "check":
                    setBetAction(action, 0L);
                    break;
                case "all_in":
                    setBetAction(action, data.getChips());
                    break;
                case "call":
                    setBetAction(action, data.getMinBet());
                    break;
                case "raise":
                    setBetAction(action, raiseRateValue);
                    break;
                default:
                    log.error("未知操作类型：{}", op);
            }
            // 可过牌时不弃牌
            if (action.getOps().equals(Ops.FOLD) && json.contains("check")){
                setBetAction(action, 0L);
            }

            return action;
        } catch (JsonProcessingException e) {
            log.error("解析AI响应数据出错");
            throw new RuntimeException(e);
        }
    }

    private static void setBetAction(ActionParams action, Long chips) {
        action.setOps(Ops.BET);
        action.setChips(chips);
    }
}
