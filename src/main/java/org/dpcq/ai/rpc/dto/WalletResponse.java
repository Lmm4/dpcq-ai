package org.dpcq.ai.rpc.dto;

import lombok.Data;

@Data
public class WalletResponse {
    /**
     * 关联ID
     */
    private Long relationId;

    /**
     * 是否成功
     */
    private Boolean success;
    /**
     * 信息
     */
    private String msg;

}
