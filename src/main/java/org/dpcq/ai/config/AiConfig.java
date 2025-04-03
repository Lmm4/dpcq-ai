package org.dpcq.ai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("ai.api")
@Data
public class AiConfig {
    private String deepseekUrl;
    private String deepseekKey;
    private String siliconflowUrl;
    private String siliconflowKey;
}
