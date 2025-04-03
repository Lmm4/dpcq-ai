package org.dpcq.ai.enums;


public enum SeatStatus {
    /**
     * 空闲中
     */
    free,

    /**
     * 等待中
     */
    wait,

    /**
     * 带入中，所锁定该某个位置
     */
    bring,


    /**
     * 已经加入了游戏
     */
    started;
//    ,

//    settle;

    public void hand() {

    }
}

/**
 * 数据校验
 * <p>
 * 修改内存
 * <p>
 * <p>
 * <p>
 * 推送通知
 * <p>
 * 下一步该做什么
 */