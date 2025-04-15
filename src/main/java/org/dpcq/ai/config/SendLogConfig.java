package org.dpcq.ai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("robot")
@Data
public class SendLogConfig {
    private boolean sendLog;
}
