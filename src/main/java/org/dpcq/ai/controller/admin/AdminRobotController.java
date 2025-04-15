package org.dpcq.ai.controller.admin;

import com.dpcq.base.annotation.AnonymousAccess;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.dpcq.ai.pojo.req.RobotConnectParam;
import org.dpcq.ai.pojo.req.RobotIdParam;
import org.dpcq.ai.pojo.req.UpdateRobotAddStatusParam;
import org.dpcq.ai.service.RobotService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/robot")
@RequiredArgsConstructor
public class AdminRobotController {
    private final RobotService robotService;

    /**
     * 新建机器人
     */
    @PostMapping("create")
    public Boolean createRobot(HttpServletRequest request) {
        return robotService.createRobot(request);
    }

    /**
     * 开启机器人进入牌桌
     */
    @PostMapping("connect")
    @AnonymousAccess
    public Boolean connectRobot(@Validated @RequestBody RobotConnectParam param) {
        return robotService.connectGame(param);
    }

    /**
     * 修改机器人筹码不足时是否带入状态
     */
    @PostMapping("updateAddStatus")
    public Boolean updateRobotAddStatus(@Validated @RequestBody UpdateRobotAddStatusParam param) {
        return robotService.updateRobotAddStatus(param.getRobotId(), param.isSupplement());
    }
    /**
     * 本手后站起
     */
    @PostMapping("leaveNext")
    @AnonymousAccess
    public Boolean list(@RequestBody RobotIdParam param) {
        robotService.leaveSeatNext(String.valueOf(param.getUserId()));
        return true;
    }
}
