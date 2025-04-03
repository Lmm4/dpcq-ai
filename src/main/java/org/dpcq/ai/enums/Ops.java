package org.dpcq.ai.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Ops {
    /**
     * 心跳
     */
    HEARTBEAT,

    /**
     * 开始游戏
     */
    START_GAME,

    /**
     * 观战牌桌
     */
    WATCH_TABLE,

    /**
     * 取消观战牌桌
     */
    CANCEL_WATCH_TABLE,

    /**
     * 牌桌数据同步
     */
    TABLE_DATA_SYNC,
    TABLE_DATA_SYNC_OTHERS,

    /**
     * 玩家入座
     */
    TAKE_SEAT,
    /**
     * 机器人入座
     */
    ROBOT_TAKE_SEAT,

    /**
     * 玩家离座
     */
    LEAVE_SEAT, TASK_LEAVE_SEAT,

    NEXT_ROUND_LEAVE_SEAT,
    /**
     * 管理员踢人
     */
    ADMIN_KICKED_OUT,
    /**
     * 管理员踢人取消
     */
    ADMIN_KICK_OUT_CANCEL,
    /**
     * 带入
     */
    GAME_BRING_IN,
    /**
     * 鱿鱼初始化
     */
    SQUID_INIT,

    /**
     * 鱿鱼带入
     */
    SQUID_BRING_IN,

    /**
     * 鱿鱼最终结算，公布最终结算榜单
     */
    SQUID_FINAL_SETTLE,

    /**
     * 鱿鱼结算，发放鱿鱼
     */
    SQUID_SETTLE,
    BRING_IN,
    BRING_OUT,
    /**
     * 通知用户下注
     */
    NOTIFY_BET,
    NOTIFY_CLASSIC_BUY,
    NOTIFY_CLASSIC_BUY_OTHERS,
    NOTIFY_BET_OTHER,
    PUBLISH_HANDS,
    BET,
    BET_ALL_IN,
    BET_SMALL,
    BET_BIG,
    BET_DELAY,
    BET_TIMEOUT,
    CHECK,
    FOLD,
    SHOW_FOLD_CARDS,
    PRE_SHOW_FOLD_CARDS,
    SHOW_PUBLIC,
    BUY_INSURANCE,
    BUY_INSURANCE_TIMEOUT,
    CANCEL_BUY_INSURANCE,
    DEV_TOOL_SELF_INFO,

    /**
     * 回退筹码
     */
    RETURN_CHIPS,

    /**
     * 比牌
     */
    COMPARE_HANDS,

    /**
     * 发公牌
     */
    PUBLISH_BOARD,

    /**
     * 切换阶段
     */
    CHANGE_STAGE,

    /**
     * 结算
     */
    SETTLE,

    /**
     * 操作延时
     */
    OPERATION_DELAY,
    /**
     * 操作延时
     */
    NEW_OPERATION_DELAY,

    /**
     * 清理对局
     */
    CLEAR_ROUND,

    /**
     * 猎兔看牌
     */
    CONSUME_GET_BOARD,
    /**
     * 猎兔看牌
     */
    NEW_CONSUME_GET_BOARD,
    /**
     * 下手更多带入
     */
    BRING_IN_NEXT,
    /**
     * 下手指定带入
     */
    BRING_SET_IN_NEXT,
    /**
     * 下手带出
     */
    BRING_OUT_NEXT,
    /**
     * 自动带入
     */
    BRING_IN_AUTO,

    /**
     * 异步渲染用户
     */
    USER_SYNC,

    /**
     * 关闭牌桌
     */
    CLOSE_TABLE, OFFLINE, CLASSIC_BUY_TIMEOOUT, CLASSIC_BUY, CLASSIC_HIT,

    /**
     * 表情包发送
     */
    EMOJI,

    /**
     * 同步聊天室聊天记录
     */
    CHAT_ROOM,

    /**
     * 收发聊天记录
     */
    CHAT_MSG, STRADDLE,

    /**
     * 普通筹码不足
     */
    CHIPS_LESS,

    /**
     * 鱿鱼筹码不足
     */
    SQUID_CHIPS_LESS,

    /**
     * 用户挂机通知
     */
    USER_HANG_UP_NOTIFY,

    /**
     * 用户是否在线通知
     */
    CANCEL_HANG_UP,

    /**
     * AOF 带入设置
     */
    AOF_BRING_SETTING,

    /**
     * 机器人数据
     */
    ROBOT_DATA,

}