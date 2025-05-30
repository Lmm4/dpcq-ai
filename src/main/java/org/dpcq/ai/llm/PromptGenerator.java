package org.dpcq.ai.llm;

import com.alibaba.nacos.common.utils.StringUtils;
import com.dpcq.base.utils.JsonUtils;
import org.dpcq.ai.enums.*;
import org.dpcq.ai.llm.dto.TableData;

public class PromptGenerator {

    /**
     * 身份信息
     */
    public static String getSystemContent(Integer characterId){
        return PromptTemplate.SYSTEM_CONTENT.format(RobotCharacter.getRobotCharacter(characterId));
    }

    public static String getUserContent(TableData data){
        StringBuilder prompt = new StringBuilder (512);
        // 牌局信息
        appendTemplate(prompt, PromptTemplate.TABLE_INFO,
                data.getPlayerCount(),
                data.getPlayers(),
                data.getMainPool(),
                data.getSidePool(),
                data.getSeatIndex(),
                data.getChips(),
                Stage.getStageDesc(data.getStage()),
                data.getMinBet());
        // 最大下注
        if (data.getMaxBet() != null && data.getMaxBet() > 0){
            appendTemplate(prompt, PromptTemplate.MAX_BET, data.getMaxBet());
        }
        // 玩家动作
        if (data.getActions() != null && !data.getActions().isEmpty()){
            for (TableData.Action action : data.getActions()) {
                String actionFormat = ActionTemplate.format(action.getEvent(), action.getSeatIndex(), action.getBet());
                if (StringUtils.isNotBlank(actionFormat)){
                    prompt.append(actionFormat);
                }
//                appendTemplate(prompt, PromptTemplate.ACTION, ActionTemplate.format(action.getEvent(),action.getSeatIndex(),action.getBet()), action.getChips());
            }
        }
        // 手牌公牌
        appendTemplate(prompt, PromptTemplate.HAND_CARDS, data.getHandCards());
        if (StringUtils.isNotBlank(data.getPubCards())){
            appendTemplate(prompt, PromptTemplate.PUB_CARDS, data.getPubCards());
        }
        // 剩下操作
        String json = GameProcessEvent.getUserEvent(data.getMinBet(), data.getChips());
        prompt.append(PromptTemplate.QUESTION.format(data.getStage().equals(Stage.PRE_FLOP.getStage()) ? PromptTemplate.PRE_FLOP.getPattern() : "", GameProcessEvent.getUserEvent(data.getMinBet(), data.getChips())));
        if (json.contains("amount")){
            prompt.append(PromptTemplate.AMOUNT.getPattern());
        }
        // 河牌圈牌型判断
        if (data.getStage().equals(Stage.RIVER.getStage())){
            prompt.append(PromptTemplate.EXTRA.getPattern());
        }
        return prompt.toString();
    }

    // 通用模板追加方法
    private static void appendTemplate(StringBuilder builder, PromptTemplate template, Object... args) {
        Object[] safeArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            safeArgs[i] = args[i] != null ? args[i] : "";
        }
        builder.append(template.format(safeArgs)).append("\n");
    }


    public static void main(String[] args) {
        String content = "{\"ops\":\"ROBOT_DATA\",\"code\":0,\"version\":0,\"characterId\":null,\"timeout\":1743496974868,\"playerCount\":2,\"players\":\"BB,BTN\",\"stage\":\"PRE_FLOP\",\"mainPool\":0,\"sidePool\":0,\"seatIndex\":\"BTN\",\"chips\":360,\"minBet\":10,\"maxBet\":360,\"handCards\":\"黑桃 5,方块 3\",\"pubCards\":\"\",\"actions\":[{\"seatIndex\":\"BTN\",\"event\":\"small\",\"bet\":10,\"chips\":null},{\"seatIndex\":\"BB\",\"event\":\"big\",\"bet\":20,\"chips\":null}]}";
        TableData data = JsonUtils.parse(content, TableData.class);
//        TableData data = new TableData();
//        data.setPlayerCount(3);
//        data.setPlayers("BB,BTS");
//        data.setMainPool(1000L);
//        data.setChips(200L);
//        data.setSidePool(100L);
//        data.setSeatIndex("BB");
//        data.setStage("FLOP");
//        data.setHandCards("红桃A，黑桃4");
//        data.setPubCards("梅花7，梅花3，方片4");
//        data.setCharacterId(1);
//        data.setMinBet(10L);
//        data.setActions(List.of(new TableData.Action("BB", "raise", 50L,100L )));
        System.out.println(getUserContent(data));
        System.out.println(getUserContent(data).length());
    }
}
