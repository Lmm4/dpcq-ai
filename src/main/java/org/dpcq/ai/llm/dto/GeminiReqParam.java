package org.dpcq.ai.llm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class GeminiReqParam {
    private List<Content> contents;


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Accessors(chain = true)
    public static class Content{
        private List<Part> parts;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Accessors(chain = true)
    public static class Part{
        private String text;
    }

    public static GeminiReqParam getParam(String text) {
        return new GeminiReqParam(List.of(new Content().setParts(List.of(new Part().setText(text)))));
    }
}
