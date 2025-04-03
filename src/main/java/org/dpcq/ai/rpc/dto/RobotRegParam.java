package org.dpcq.ai.rpc.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RobotRegParam {

    /**
     * 用户名
     */
    @NotNull
    @Size(min = 6,max = 15)
    private String username;

    /**
     * 密码
     */
    @NotNull
    @Size(min = 6,max = 15)
    private String password;

    /**
     * ip
     */
    private String ip;
}
