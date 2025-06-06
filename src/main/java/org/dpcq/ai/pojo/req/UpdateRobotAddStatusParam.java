package org.dpcq.ai.pojo.req;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateRobotAddStatusParam {
    @NotNull
    private Long robotId;
    @NotNull
    private boolean supplement;
}
