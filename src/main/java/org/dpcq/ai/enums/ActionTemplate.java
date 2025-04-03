package org.dpcq.ai.enums;

import lombok.Getter;

@Getter
public enum ActionTemplate {

    ACTION_FOLD("fold","%s位弃牌，"),
    ACTION_BIG("big","%s位一倍大盲，"),
    ACTION_SMALL("small","%s位一倍小盲，"),
    ACTION_CHECK("check","%s位过牌，"),
    ACTION_CALL("call","%s位跟注%d，"),
    ACTION_RAISE("raise","%s位加注%d，"),
    ACTION_RAISE_ALL("all_in","%s位 AllIn，");

    private final String stage;
    private final String pattern;

    ActionTemplate(String stage,String pattern) {
        this.stage = stage;
        this.pattern = pattern;
    }

    public static String format(String stage,Object... args) {
        for (ActionTemplate actionTemplate : ActionTemplate.values()){
            if (actionTemplate.getStage().equals(stage)){
                return String.format(actionTemplate.getPattern(),args);
            }
        }
        return "";
    }
}
