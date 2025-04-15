package org.dpcq.ai.llm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeminiResponse {

    private List<Candidate> candidates;
    private JsonNode usageMetadata;
    private String modelVersion;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Candidate {
        private GeminiReqParam.Content content;
        private String finishReason;
        private Long avgLogprobs;
    }
}
