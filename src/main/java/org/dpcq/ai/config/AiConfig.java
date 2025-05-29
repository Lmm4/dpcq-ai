package org.dpcq.ai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "ai")
public class AiConfig {
    private Ollama ollama;
    private Api api;

    @Data
    public static class Ollama {
        private String baseUrl;
        private Chat chat;
        @Data
        public static class Chat {
            private Options options;
            @Data
            public static class Options {
                private String model;
            }
        }
    }
    @Data
    public static class Api {
        private ApiProvider deepseek;
        private ApiProvider siliconflow;
        private ApiProvider gemini;
        private ApiProvider qwen;

        @Data
        public static class ApiProvider {
            private String url;
            private List<String> key;
        }
    }
}