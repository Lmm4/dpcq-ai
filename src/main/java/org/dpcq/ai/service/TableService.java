package org.dpcq.ai.service;

import com.dpcq.base.enums.SymbolEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dpcq.ai.entity.RobotEntity;
import org.dpcq.ai.repo.impl.RobotRepo;
import org.dpcq.ai.rpc.FeignClubApi;
import org.dpcq.ai.rpc.dto.FreeTableVo;
import org.dpcq.ai.rpc.dto.RobotTableAddReqParam;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class TableService {
    /**
     机器人规则
     1、每次创建牌局的时候，需要保证机器人是房主的牌局要有最少10个，如果少于10个，需要在随机挑选一个机器人主动创建牌局，创建后自动加入自己创建的房间

     2、房间创建规则
     -房间名称机器人自己昵称，【***的牌局】
     -牌局的大小盲注，随机（1/2 2/4 4/8  6/12 8/16 10/20）
     -牌局默认公开大厅
     -默认不开保险
     -默认不开抢抓
     -默认带入限制
     -默认开启同ip限制
     -默认选择全底池3%抽水
     -默认不开鱿鱼，
     -牌局创建时间默认1~5小时
     -自动开局人数2人
     -其他默认，不开启或默认选项

     3、40个作为房间加入者，

     4、系统需要实时判断机器人是否有闲置状态，如果有，则执行加入规则，反之停止

     5、加入者的加入规则：
     -牌局每3分钟检测一次：
     前提：机器人只会加入，大盲小于等于20BB的，反之不会加入
     1、牌局总人数：7人以上（机器人不做处理）
     2、符合以上要求后，判断真人数量  <=6  如果是（机器人加入 1个）如果不是（不处理）

     6、机器人闲置规则
     1、机器人全部筹码输光之后，退出牌局进入闲置状态，并补充筹码。机器人的初始筹码为：2000
     2、机器人配踢出牌局，进入闲置状态，并补充筹码。机器人的初始筹码为：2000
     */
    private final RobotRepo robotRepo;
    private final FeignClubApi feignClubApi;
    Random random = new Random();

    public long getRobotTable(){
        List<FreeTableVo> freeTableInfo = feignClubApi.getFreeTableInfo();
        List<RobotEntity> robotList = robotRepo.lambdaQuery().eq(RobotEntity::getStatus, 1).list();
        if (freeTableInfo.isEmpty()){
            return 0;
        }
        return freeTableInfo.stream().filter(freeTableVo -> robotList.contains(freeTableVo.getCreateBy())).count();
    }

    public Long createTableByRobot(Long userId) {
        return feignClubApi.createTableByRobot(generalTable(userId));
    }

    private RobotTableAddReqParam generalTable(Long userId) {
        RobotTableAddReqParam param = new RobotTableAddReqParam();
        param.setUserId(userId);
        param.setAnte(0L);    //无前注
        Map<String,Long> blind = getBlind();
        param.setPwd("");
        param.setBig(blind.get("big")); //大盲
        param.setSmall(blind.get("small"));//小盲
        param.setBringUpper(blind.get("big") * 150);//最大带入
        param.setBringLower(blind.get("small") * 20);//最小带入
        param.setGameDuration(getGameDuration());//牌局时间
        param.setType("classic_texas");
        param.setCurrencyType(SymbolEnum.DPCQ.getSymbol());
        param.setAutoStartPlayers(2);//自动两人开局
        param.setStraddle(false);
        param.setInsuranceFlag(false);
        param.setGpsStintFlag(false);
        param.setLiveVideoFlag(false);
        param.setOnlyIosFlag(false);
        param.setSimulatorStintFlag(false);
        param.setPreFlopStintFlag(false);
        param.setCumulativeBring(-1L);//累计带入限制
        param.setSameIpStintFlag(true);//ip限制
        param.setPoolEntryRateLimit(10);//入池率限制
        RobotTableAddReqParam.ChargeConfig chargeConfig = new RobotTableAddReqParam.ChargeConfig();
        chargeConfig.setPerLotServer("RATE_CHARGE");
        chargeConfig.setCalcMethod("POT");
        chargeConfig.setCalcRate(3L);
        chargeConfig.setPreFlopFreeFlag(false);
        chargeConfig.setThreePeopleOffFlag(false);
        chargeConfig.setPotBelowFreeFlag(false);
        chargeConfig.setPotBelowFree(0L);
        chargeConfig.setServiceFeeCap(-1L);
        param.setChargeConfig(chargeConfig);
        return param;
    }

    /**
     *  随机大小盲注
     */
    private Map<String,Long> getBlind(){
        // 盲注选项
        String[] blinds = {"1/2", "2/4", "4/8", "6/12", "8/16", "10/20"};
        // 随机选择一个盲注
        String selectedBlind = blinds[random.nextInt(blinds.length)];
        return Map.of("small", Long.valueOf(selectedBlind.split("/")[0]),
                "big", Long.valueOf(selectedBlind.split("/")[1]));
    }

    /**
     * 随机牌局时间
     */
    private int getGameDuration(){
        int[] gameDurations = {3600, 7200, 10800, 14400, 18000};
        return gameDurations[random.nextInt(gameDurations.length)];
    }

}
