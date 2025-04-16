package org.dpcq.ai.rpc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletMsg {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 变动金额
     */
    private Long amount;

    /**
     * 货币符号
     */
    private String symbol;

    /**
     * 交易类型
     */
    private String type;

    /**
     * 备注
     */
    private String memo;

    /**
     * 关联ID
     */
    private Long relationId;

    /**
     * 账户角色,不传默认USER /CLUB/UNION
     */
    private String roleType;
}
