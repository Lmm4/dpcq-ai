package org.dpcq.ai.enums;

import com.dpcq.base.utils.JsonUtils;
import lombok.Getter;

import java.util.*;

/**
 * 游戏状态
 */
@Getter
public enum GameProcessEvent {
    /**
     * 小盲
     */
    small("small","小盲"),

    /**
     * 大盲
     */
    big("big","大盲"),

    /**
     * 过牌
     */
    check("check","过牌"),

    /**
     * 跟注
     */
    call("call","跟注"),

    /**
     * 加注
     */
    raise("raise","加注"),

    /**
     * 强抓
     */
    straddle("straddle","强抓"),

    /**
     * 弃牌
     */
    fold("fold","弃牌"),

    /**
     * all_in
     */
    all_in("all_in","All-In"),
    ;

    private final String event;
    private final String desc;

    GameProcessEvent(String event, String desc) {
        this.event = event;
        this.desc = desc;
    }

    public static String getEvent(String event) {
        for (GameProcessEvent value : values()) {
            if (value.event.equals(event)) {
                return value.getDesc();
            }
        }
        return null;
    }

    /**
     * 用户可行操作
     */
    public static String getUserEvent(Long minBet, Long chips) {
        Map<String,String> map = new HashMap<>();
        map.put(fold.getEvent(), "");
        if (minBet == 0){
            map.put(check.getEvent(), "");
            map.put(raise.getEvent(), "");
            map.put(all_in.getEvent(), "");
            map.put("amount","");
        }
        if (Objects.equals(chips, minBet)){
            map.put(all_in.getEvent(), "");
        }
        if (minBet < chips){
            map.put(call.getEvent(), "");
            map.put(raise.getEvent(), "");
            map.put(all_in.getEvent(), "");
            map.put("amount","");
        }
        return JsonUtils.toJsonString(map);
    }


//    /**
//     * 保险
//     */
//    ins("",""),
//
//    /**
//     * 保险命中
//     */
//    ins_hit("",""),

//    /**
//     * 退还
//     */
//    refund("",""),

//    /**
//     * 秀牌
//     */
//    show
}
