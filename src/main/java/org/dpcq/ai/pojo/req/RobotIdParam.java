package org.dpcq.ai.pojo.req;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RobotIdParam {
    /**
     * 机器人userid
     */
    private Long userId;
}
