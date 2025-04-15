package org.dpcq.ai.rpc;

import org.dpcq.ai.rpc.dto.RobotRegParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "club")
public interface FeignClubApi {

    @PostMapping("register/robot")
    Long registerRobot(@RequestBody RobotRegParam body);

}
