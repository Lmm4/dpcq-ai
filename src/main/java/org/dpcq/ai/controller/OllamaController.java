package org.dpcq.ai.controller;

import com.dpcq.base.annotation.AnonymousAccess;
import jakarta.annotation.Resource;
import org.dpcq.ai.llm.dto.TableData;
import org.dpcq.ai.service.RobotService;
import org.dpcq.ai.util.MdToJsonUtil;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.web.bind.annotation.*;

@RestController
public class OllamaController {
    @Resource
    private OllamaChatModel ollamaChatModel;
    @Resource
    private RobotService robotService;
    @RequestMapping(value = "/ai/ollama/gemma")
    @AnonymousAccess
    public Object ollamaGemma(@RequestParam(value = "msg") String msg) {
        ChatResponse chatResponse = ollamaChatModel.call(
                new Prompt(msg, OllamaOptions.builder()
                        .model("gemma3:27b")//指定使用哪个大模型
                        .temperature(0.5).build()));
        return MdToJsonUtil.convert(chatResponse.getResult().getOutput().getText());
    }
    @PostMapping("/test/robot/{model}")
    @AnonymousAccess
    public String testRobot(@PathVariable("model")String model, @RequestBody TableData data) throws Exception {
        return switch (model) {
            case "gemma" -> robotService.getGemmaResponse(data);
            case "V3" -> robotService.getV3Response(data);
            case "gemini" -> robotService.getGeminiResponse(data);
            default -> "";
        };
    }


}
