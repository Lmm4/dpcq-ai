package org.dpcq.ai.service;

import cn.hutool.core.lang.UUID;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dpcq.ai.entity.RobotEntity;
import org.dpcq.ai.enums.RobotCharacter;
import org.dpcq.ai.llm.PromptGenerator;
import org.dpcq.ai.llm.dto.TableData;
import org.dpcq.ai.llm.model.DeepSeekV3ApiModel;
import org.dpcq.ai.llm.model.GeminiModel;
import org.dpcq.ai.llm.model.GemmaOllamaModel;
import org.dpcq.ai.pojo.req.RobotConnectParam;
import org.dpcq.ai.repo.IRobotRepo;
import org.dpcq.ai.rpc.FeignUserApi;
import org.dpcq.ai.rpc.dto.RobotRegParam;
import org.dpcq.ai.socket.SessionHandler;
import org.dpcq.ai.socket.WebSocketConnectionManager;
import org.dpcq.ai.socket.handler.dto.RobotInfo;
import org.dpcq.ai.util.ServletIpUtil;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RobotService {
    private final FeignUserApi feignUserApi;
    private final DeepSeekV3ApiModel deepSeekV3ApiModel;
    private final GemmaOllamaModel gemmaOllamaModel;
    private final IRobotRepo robotRepo;
    private final WebSocketConnectionManager connectionManager;
    private final OpsService opsService;

    /**
     * 创建机器人
     */
    public boolean createRobot(HttpServletRequest request){
        RobotRegParam form = new RobotRegParam();
        form.setUsername("robot" + System.currentTimeMillis());
        form.setPassword(UUID.fastUUID().toString(true).substring(0,14));
        form.setIp(ServletIpUtil.getClientIP(request));
        Long userId = feignUserApi.registerRobot(form);
        robotRepo.save(RobotEntity.builder()
                        .characters(RobotCharacter.getRandomRobotCharacter())
                        .userId(userId)
                        .status(1)
                        .build());
        return true;
    }

    /**
     * 连接游戏
     */
    public boolean connectGame(RobotConnectParam param){
        RobotEntity robot = robotRepo.getById(param.getRobotId());
        if (robot == null) {
            throw new RuntimeException("机器人不存在");
        }
        if (robot.getStatus() == 0) {
            throw new RuntimeException("机器人已关闭");
        }
        if (connectionManager.isRobotInGame(robot.getUserId().toString())) {
            throw new RuntimeException("机器人已连接");
        }

        RobotInfo robotInfo = new RobotInfo();
        robotInfo.setSupplement(robot.isSupplement());
        robotInfo.setCharacterId(robot.getCharacters());
        robotInfo.setUserId(robot.getUserId().toString());
        robotInfo.setTableId(param.getTableId().toString());
        connectionManager.createConnection(robotInfo);
        return true;
    }

    /**
     * 修改机器人筹码不足时是否带入状态
     */
    public boolean updateRobotAddStatus(Long robotId, boolean supplement) {
        RobotEntity robot = robotRepo.getById(robotId);
        if (robot == null) {
            throw new RuntimeException("机器人不存在");
        }
        robot.setSupplement(supplement);
        robot.setUpdateAt(new Date());
        boolean b = robotRepo.updateById(robot);
        SessionHandler sessionByUserId = connectionManager.getSessionByUserId(robot.getUserId().toString());
        if (sessionByUserId != null){
            sessionByUserId.getRobotInfo().setSupplement(supplement);
        }
        return b;
    }

    /**
     * 获得空闲机器人
     */
    public RobotEntity getFreeRobot(){
        Collection<String> activeUserIds = connectionManager.getActiveUserIds();
        List<RobotEntity> list = robotRepo.lambdaQuery().eq(RobotEntity::getStatus, 1)
                .notIn(!activeUserIds.isEmpty(), RobotEntity::getUserId, activeUserIds)
                .list();
        if (list.isEmpty()){
            return null;
        }
        Collections.shuffle(list);
        return list.get(0);
    }

    /**
     * 下一手离桌
     */
    public void leaveSeatNext(String userId){
        SessionHandler session = connectionManager.getSessionByUserId(userId);
        opsService.leaveSeatNext(userId, session);
    }

    /**
     * 空闲机器人列表
     */
    public List<RobotEntity> getFreeRobotList(){
        Collection<String> activeUserIds = connectionManager.getActiveUserIds();
        return robotRepo.lambdaQuery().eq(RobotEntity::getStatus, 1)
                .notIn(RobotEntity::getUserId,activeUserIds)
                .list();
    }


    public String getV3Response(TableData data) throws Exception {
        return deepSeekV3ApiModel.getResponse("",PromptGenerator.getUserContent(data));
    }

    public String getGemmaResponse(TableData data) {
        return gemmaOllamaModel.getResponse("",PromptGenerator.getUserContent(data));
    }

    private final GeminiModel geminiModel;

    public String getGeminiResponse(TableData data) {
        return geminiModel.getResponse("",PromptGenerator.getUserContent(data));
    }

}
