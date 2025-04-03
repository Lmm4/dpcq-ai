package org.dpcq.ai.llm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponsJsonObj {
    private String fold;
    private String check;
    private String raise;
    private String all_in;
    private String amount;
}
