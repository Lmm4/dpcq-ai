package org.dpcq.ai.util;


import cn.hutool.core.collection.CollectionUtil;

import java.util.*;

public class CardUtil {
    public static final Map<String, Integer> SHORT_CARD_MAP = new HashMap<>();
    public static final Map<String, Integer> CARD_MAP = new HashMap<>();

    static {
        SHORT_CARD_MAP.put("6c", 16);
        SHORT_CARD_MAP.put("6d", 17);
        SHORT_CARD_MAP.put("6h", 18);
        SHORT_CARD_MAP.put("6s", 19);
        SHORT_CARD_MAP.put("7c", 20);
        SHORT_CARD_MAP.put("7d", 21);
        SHORT_CARD_MAP.put("7h", 22);
        SHORT_CARD_MAP.put("7s", 23);
        SHORT_CARD_MAP.put("8c", 24);
        SHORT_CARD_MAP.put("8d", 25);
        SHORT_CARD_MAP.put("8h", 26);
        SHORT_CARD_MAP.put("8s", 27);
        SHORT_CARD_MAP.put("9c", 28);
        SHORT_CARD_MAP.put("9d", 29);
        SHORT_CARD_MAP.put("9h", 30);
        SHORT_CARD_MAP.put("9s", 31);
        SHORT_CARD_MAP.put("Tc", 32);
        SHORT_CARD_MAP.put("Td", 33);
        SHORT_CARD_MAP.put("Th", 34);
        SHORT_CARD_MAP.put("Ts", 35);
        SHORT_CARD_MAP.put("Jc", 36);
        SHORT_CARD_MAP.put("Jd", 37);
        SHORT_CARD_MAP.put("Jh", 38);
        SHORT_CARD_MAP.put("Js", 39);
        SHORT_CARD_MAP.put("Qc", 40);
        SHORT_CARD_MAP.put("Qd", 41);
        SHORT_CARD_MAP.put("Qh", 42);
        SHORT_CARD_MAP.put("Qs", 43);
        SHORT_CARD_MAP.put("Kc", 44);
        SHORT_CARD_MAP.put("Kd", 45);
        SHORT_CARD_MAP.put("Kh", 46);
        SHORT_CARD_MAP.put("Ks", 47);
        SHORT_CARD_MAP.put("Ac", 48);
        SHORT_CARD_MAP.put("Ad", 49);
        SHORT_CARD_MAP.put("Ah", 50);
        SHORT_CARD_MAP.put("As", 51);
    }

    static {
        CARD_MAP.put("2c", 0);
        CARD_MAP.put("2d", 1);
        CARD_MAP.put("2h", 2);
        CARD_MAP.put("2s", 3);
        CARD_MAP.put("3c", 4);
        CARD_MAP.put("3d", 5);
        CARD_MAP.put("3h", 6);
        CARD_MAP.put("3s", 7);
        CARD_MAP.put("4c", 8);
        CARD_MAP.put("4d", 9);
        CARD_MAP.put("4h", 10);
        CARD_MAP.put("4s", 11);
        CARD_MAP.put("5c", 12);
        CARD_MAP.put("5d", 13);
        CARD_MAP.put("5h", 14);
        CARD_MAP.put("5s", 15);
        CARD_MAP.putAll(SHORT_CARD_MAP);
    }

    /**
     * 取得牌ID
     *
     * @param card
     * @return
     */
    public static Integer getId(String card) {
        return CARD_MAP.get(card);
    }

    public static String transferCardToChinese(String card) {
        var number = card.substring(0, 1);
        if (Objects.equals("T", number)) {
            number = "10";
        }
        var color = switch (card.charAt(1)) {
            case 'c' -> "梅花";
            case 'd' -> "方块";
            case 'h' -> "红桃";
            default -> "黑桃";
        };
        return color + " " + number;
    }

    public static String transferChineseToCard(String cCard){
        String color = cCard.substring(0, 2);
        String number = cCard.substring(2, cCard.length() - 1);
        if (Objects.equals("10", number)){
            number = "T";
        }
        color = switch (color){
            case "梅花" -> "c";
            case "方块" -> "d";
            case "红桃" -> "h";
            default -> "s";
        };
        return number + color;
    }

    public static Collection<String> transferCardToChinese(Collection<String> cards) {
        if (CollectionUtil.isEmpty(cards)) {
            return Collections.emptyList();
        }
        return cards.stream().map(CardUtil::transferCardToChinese).toList();
    }
}
