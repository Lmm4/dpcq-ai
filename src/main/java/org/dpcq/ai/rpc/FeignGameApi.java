package org.dpcq.ai.rpc;

import org.dpcq.ai.rpc.dto.FreeTableVo;
import org.dpcq.ai.rpc.dto.RobotRegParam;
import org.dpcq.ai.rpc.dto.RobotTableAddReqParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "dp-game")
public interface FeignGameApi {

    @GetMapping("/internal/tablesCanJoin")
    List<Long> tablesCanJoin();

}
