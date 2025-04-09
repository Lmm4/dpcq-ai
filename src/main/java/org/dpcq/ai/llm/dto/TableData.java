package org.dpcq.ai.llm.dto;

import lombok.*;
import org.dpcq.ai.socket.handler.dto.Notify;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class TableData extends Notify {
    /**
     * 用户风格
     */
    private Integer characterId;
    /**
     * 牌桌id
     */
    private Long tableId;
    /**
     * 下注凭证
     */
    private Long timeout;
    /**
     * 玩家数量
     */
    private Integer playerCount;
    /**
     * 玩家位置
     */
    private String players;
    /**
     * 当前阶段
     */
    private String stage;
    /**
     * 主池筹码
     */
    private Long mainPool;
    /**
     * 边池筹码
     */
    private Long sidePool;
    /**
     * 位置
     */
    private String seatIndex;
    /**
     * 可用筹码
     */
    private Long chips;
    /**
     * 最低跟注
     */
    private Long minBet;
    /**
     * 最大下注金额
     */
    private Long maxBet;
    /**
     * 手牌
     */
    private String handCards;
    /**
     * 公牌
     */
    private String pubCards;
    /**
     * 其它玩家动作
     */
    private List<Action> actions;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Action {
        // 座位号
        private String seatIndex;
        // 最新动作
        private String event;
        // 加注数量
        private Long bet;
        // 剩余筹码
        private Long chips;
    }
}
