package org.dpcq.ai.pojo.req;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RobotListQueryParam {
    /**
     * 机器人userid
     */
    private Long userId;
    /**
     * 是否连接中
     */
    private Integer isConnect;
}
