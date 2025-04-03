package org.dpcq.ai.enums;

import lombok.Getter;

@Getter
public enum RobotCharacter {

    RADICAL(1,"激进(尽量不弃牌,谨慎ALLIN)"),
    STEADY(2,"稳健(专注手牌，不受他人影响)"),
    CONSERVATISM(3,"保守"),
    ;

    private final int id;
    private final String desc;

    RobotCharacter(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public static String getRobotCharacter(int id) {
        for (RobotCharacter robotCharacter : RobotCharacter.values()) {
            if (robotCharacter.getId() == id) {
                return robotCharacter.getDesc();
            }
        }
        return null;
    }
    /**
     * 随机获得一个机器人性格
     */
    public static Integer getRandomRobotCharacter() {
        int random = (int) (Math.random() * 3) + 1;
        return RobotCharacter.values()[random - 1].id;
    }
}
