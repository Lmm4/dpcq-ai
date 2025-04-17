package org.dpcq.ai.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dpcq.ai.entity.RobotEntity;
import org.dpcq.ai.pojo.req.RobotConnectParam;
import org.dpcq.ai.rpc.FeignGameApi;
import org.dpcq.ai.service.RobotService;
import org.dpcq.ai.service.TableService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 创建机器人牌桌任务
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class TableTask {
    private final TableService tableService;
    private final RobotService robotService;
    // 最少房间数
    private final int minTableNum = 2;
    private final FeignGameApi feignGameApi;
    /**
     * 定时创建牌桌
     * 每次创建牌局的时候，需要保证机器人是房主的牌局要有最少10个，如果少于10个，需要在随机挑选一个机器人主动创建牌局，创建后自动加入自己创建的房间
     */
    @Scheduled(initialDelay = 1000 * 5 , fixedDelay = 1000 * 60 * 1)
    public void createTable(){
        long num = tableService.getRobotTable();
        if (num < minTableNum){
            RobotEntity freeRobot = robotService.getFreeRobot();
            if (freeRobot == null){
                log.info("【创建牌桌失败】当前机器人牌桌：{}, 无空闲机器人",num);
                return;
            }
            Long tableId = tableService.createTableByRobot(freeRobot.getUserId());
            robotService.connectGame(new RobotConnectParam().setRobotId(freeRobot.getId()).setTableId(tableId));
            log.info("【创建牌桌成功】当前机器人牌桌：{}, 创建牌桌：{}",num,tableId);
        }
    }

    /**
     * 加入者的加入规则：
     *      -牌局每3分钟检测一次：
     *      前提：机器人只会加入，大盲小于等于20BB的，反之不会加入
     *      1、牌局总人数：7人以上（机器人不做处理）
     *      2、符合以上要求后，判断真人数量  <=6  如果是（机器人加入 1个）如果不是（不处理）
     */
    @Scheduled(initialDelay = 1000 * 10 , fixedDelay = 1000 * 60 * 3)
    public void joinTable(){
        List<RobotEntity> freeRobotList = robotService.getFreeRobotList();
        if (freeRobotList.isEmpty()){
            return;
        }
        List<Long> tableIds = feignGameApi.tablesCanJoin();
        if (tableIds.isEmpty()){
            return;
        }
        int index = tableIds.size() - 1;
        for (RobotEntity robotEntity : freeRobotList) {
            if (index < 0){
                break;
            }
            Long tableId = tableIds.get(index);
            robotService.connectGame(new RobotConnectParam().setRobotId(robotEntity.getId()).setTableId(tableId));
            index --;
        }
    }
}
