package org.dpcq.ai.enums;

import lombok.Getter;

@Getter
public enum PromptTemplate {

    SYSTEM_CONTENT("你是一个德州高手，操作风格%s"),
    TABLE_INFO("一局%d人德扑，参与人分别为%s，当前主池筹码：%d，边池：%d，你是%s位，可用筹码%d，当前是%s阶段，当前最低跟注%d"),
    MAX_BET("最高下注限制%d"),
    HAND_CARDS("你的手牌是%s"),
    PUB_CARDS("公牌是%s"),
    QUESTION("结合以上信息经过分析后,你会选择的操作的百分比各是多少,%s不返回分析过程,只返回这个模板的json:%s。"),
    PRE_FLOP("尽量不弃牌,"),
    AMOUNT("amount字段为加注的筹码数，不能为0。"),
    EXTRA("如果最大牌型能组成顺子、四条、同花、同花顺、葫芦其中之一，不选择弃牌"),
    ;

    private final String pattern;

    PromptTemplate(String pattern) {
        this.pattern = pattern;
    }

    public String format(Object... args) {
        return String.format(pattern, args);
    }
}
