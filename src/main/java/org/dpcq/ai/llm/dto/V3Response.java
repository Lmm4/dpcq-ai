package org.dpcq.ai.llm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class V3Response {
    private String id;
    private String object;
    private Long created;
    private String model;
    private String system_fingerprint;
    private List<Choice> choices;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Choice {
        private Long index;
        private V3Message message;
        private String logprobs;
        private String finish_reason;
    }
}
