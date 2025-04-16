package org.dpcq.ai.rpc;

import org.dpcq.ai.rpc.dto.FreeTableVo;
import org.dpcq.ai.rpc.dto.RobotRegParam;
import org.dpcq.ai.rpc.dto.RobotTableAddReqParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "club")
public interface FeignClubApi {

    @PostMapping("/internal/register/robot")
    Long registerRobot(@RequestBody RobotRegParam body);

    @PostMapping("/internal/createTableByRobot")
    Long createTableByRobot(@RequestBody RobotTableAddReqParam vo);
    /**
     * 查询进行中的机器人牌桌列表
     */
    @GetMapping("/internal/getFreeTableInfo")
    List<FreeTableVo> getFreeTableInfo();
}
