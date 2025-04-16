package org.dpcq.ai.rpc.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RobotTableAddReqParam {

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 俱乐部ID
     */
    private Long clubId;

    /**
     * 玩法类型
     */
    private String type;

    /**
     * 牌桌名称
     */
    private String name;

    /**
     * 加入牌局的密码
     */
    private String pwd;

    /**
     * 前注
     */
    private Long ante;

    /**
     * 小盲
     */
    private Long small;

    /**
     * 大盲
     */
    private Long big;

    /**
     * 抢抓
     */
    private Boolean straddle;

    /**
     * 是否开启保险
     */
    private Boolean insuranceFlag;

    /**
     * 带入上限
     */
    private Long bringUpper;

    /**
     * 带入下限
     */
    private Long bringLower;

    /**
     * 累计带入限制
     */
    private Long cumulativeBring;

    /**
     * 是否开启实时视频
     */
    private boolean liveVideoFlag;

    /**
     * 货币类型
     */
    private String currencyType;

    /**
     * 自动开局人数
     */
    private Integer autoStartPlayers;

    /**
     * 游戏对局时长
     */
    private Integer gameDuration;

    /**
     * 入池率限制
     */
    private Boolean poolEntryRateLimitFlag;

    /**
     * 入池率限制
     */
    private Integer poolEntryRateLimit;

    /**
     * 同IP限制是否开启
     */
    private Boolean sameIpStintFlag;

    /**
     * GPS限制是否开启
     */
    private Boolean gpsStintFlag;

    /**
     * 禁止模拟器限制是否开启
     */
    private Boolean simulatorStintFlag;

    /**
     * 仅限IOS设备
     */
    private Boolean onlyIosFlag;

    /**
     * preFlop延迟看牌限制是否开启
     */
    private Boolean preFlopStintFlag;

    /**
     * 短牌模式
     */
    private AofConfig aofConfig;

    /**
     * 收费配置
     */
    @Valid
    @NotNull
    private ChargeConfig chargeConfig;


    /**
     * 鱿鱼模式配置
     */
    private SquidConfig squidConfig;

    /**
     * 牌桌的可见方式
     */
    private String showWay;


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ChargeConfig {

        /**
         * 每手服务费
         *
         */
        @NotNull
        private String perLotServer;

        /**
         * 计算方式
         *
         */
        private String calcMethod;

        /**
         * 比例-》比例
         */
        private Long calcRate;

        /**
         * 比例-》preFlop结束免费
         */
        @NotNull
        private Boolean preFlopFreeFlag;

        /**
         * 比例-》低于三人五折（不包含三人）
         */
        @NotNull
        private Boolean threePeopleOffFlag;

        /**
         * 比例-》底池低于此值免费是否开启
         */
        @NotNull
        private Boolean potBelowFreeFlag;

        /**
         * 比例-》底池低于此值免费
         */
        private Long potBelowFree;

        /**
         * 比例-》服务费封顶
         */
        private Long serviceFeeCap;
    }

    @Setter
    @Getter
    public static class SquidConfig {

        /**
         * 筹码
         */
        private Long chips;

        /**
         * 最小用户
         */
        private Long minUser;

        /**
         * 最多用户
         */
        private Long maxUser;

        /**
         * 无动作发放鱿鱼
         */
        private Boolean noActionGiftSquid;

        /**
         * 第一次获得鱿鱼自动秀牌
         */
        private Boolean firstSquidAutoShowHands;
    }

    /**
     * 推推乐配置
     */
    @Setter
    @Getter
    public static class AofConfig {

        /**
         * 强制带入
         */
        private boolean forceBringIn;

        /**
         * 强制带出
         */
        private boolean forceBringOut;
    }



}
