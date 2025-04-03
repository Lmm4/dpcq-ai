package org.dpcq.ai.socket.handler.dto.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dpcq.ai.socket.handler.dto.Notify;

import java.util.List;
import java.util.TimeZone;

/**
 * 游戏数据同步
 */
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameDataSyncDto extends Notify {
    /**
     * 服务器时间
     */
    private long serverTime = System.currentTimeMillis();

    private String timeZoneID = TimeZone.getDefault().getID();

    /**
     * 牌桌座位
     */
    private List<SeatVo> seats;

    /**
     * 最新的庄位ID
     */
    private int lastBtnSeatId;

    /**
     * 黑名单ID
     */
    private List<Long> blackUserList;

    /**
     * 牌桌配置
     */
    private TableConfigVo tableConfig;

    /**
     * 对局数据
     */
    private RoundVo round;

    /**
     * 总计轮次
     */
    private long roundCount;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SeatVo {


        /**
         * 座位状态
         */
        private String status;

        /**
         * 座位玩家
         */
        private PlayerBassVo player;
    }
    /**
     * 对局信息
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NoArgsConstructor(force = true)
    public static class RoundVo {

        /**
         * 底池金额
         */
        private final long pot;
        /**
         * 阶段
         */
        private final String stage;
        /**
         * 牌桌ID
         */
        private long id;
        /**
         * 保险弹窗零时信息
         */
//        private NotifyClassicBuyResult.Body insInfo;

        /**
         * 底牌
         */
        private List<String> board;

        /**
         * 猎兔玩家
         */
        private List<Long> lieTuUsers;

        /**
         * 保险命中的牌
         */
        private List<String> insHitCards;

        /**
         * 清理牌桌时间
         */
        private Long clearTime;

    }

    /**
     * 牌桌配置
     */
    @Data
    @NoArgsConstructor
    public static class TableConfigVo {

        /**
         * 牌桌ID
         */
        private Long id;

        /**
         * 游戏类型
         */
        private String type;

        /**
         * 牌桌名称
         */
        private String name;

        /**
         * 小盲
         */
        private long small;

        /**
         * 大盲
         */
        private long big;

        /**
         * 强抓
         */
        private long straddle;

        /**
         * 前注
         */
        private long ante;

        /**
         * 牌桌货币符号
         */
        private String symbol;

        /**
         * 牌桌货币小数位
         */
        private int symbolDecimal;

        /**
         * 牌桌货币符号
         */
        private String symbolSign;

        /**
         * 最小带入
         */

        private long minBring;
        /**
         * 最大带入
         */
        private long maxBring;

        /**
         * 有权限管理牌桌的人
         */
        private List<Long> managers;

        /**
         * 自动开局人数
         */
        private int autoStartPlayers;

        /**
         * 俱乐部ID
         */
        private long clubId;

        /**
         * 牌局创建人名称
         */
        private String createName;

        /**
         * 牌局创建时间
         */
        private long createAt;

        /**
         * 踢人
         */
        private long createBy;

        /**
         * 开始时间
         */
        private long startAt;

        /**
         * 牌局结束时间
         */
        private long endAt;

        /**
         * 二级密码 默认没有
         */
        private String pwd = "";

        /**
         * 保险模式
         */
//        private TableConfig.InsuranceMode insuranceMode;

        /**
         * 牌桌配置时长，单位秒
         */
        private int gameDuration;

        /**
         * 是否开启实时视频
         */
        private boolean liveVideoFlag;

        /**
         * 同IP限制是否开启
         */
        private boolean sameIpStintFlag;

        /**
         * GPS限制是否开启
         */
        private boolean gpsStintFlag;

        /**
         * 禁止模拟器限制是否开启
         */
        private boolean simulatorStintFlag;

        /**
         * 仅限IOS设备
         */
        private boolean onlyIosFlag;

//        /**
//         * 推推乐模式
//         */
//        private AofConfig aofConfig;
//
//        /**
//         * 鱿鱼游戏配置
//         */
//        private SquidConfig squidConfig;

//        /**
//         * 房主的牌桌皮肤
//         */
//        private String tableSkin;

    }
}
