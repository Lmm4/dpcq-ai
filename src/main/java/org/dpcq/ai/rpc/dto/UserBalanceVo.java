package org.dpcq.ai.rpc.dto;

import lombok.Data;

@Data
public class UserBalanceVo {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 标识
     */
    private String symbol;
    /**
     * 余额
     */
    private String balance;
    /**
     * 总充值
     */
    private String rechargeTotal;
    /**
     * 总提现
     */
    private String withdrawTotal;

}
