package org.dpcq.ai.llm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class V3ReqParam {

    private List<V3Message> messages;
    private String model;
    private Boolean stream;

}
