package org.dpcq.ai.enums;

import lombok.Getter;

@Getter
public enum Stage {

    /**
     * 翻牌前
     */
    PRE_FLOP("PRE_FLOP", "翻牌前"),

    /**
     * 翻牌圈
     */
    FLOP("FLOP","翻牌圈"),

    /**
     * 转盘圈
     */
    TURN("TURN","转盘圈"),

    /**
     * 河牌圈
     */
    RIVER("RIVER","河牌圈");

    private final String stage;
    private final String desc;
    Stage(String stage, String desc){
        this.stage = stage;
        this.desc = desc;
    }

    public static String getStageDesc(String stage){
        for(Stage s : Stage.values()){
            if(s.getStage().equals(stage)){
                return s.getDesc();
            }
        }
        return "";
    }
}
