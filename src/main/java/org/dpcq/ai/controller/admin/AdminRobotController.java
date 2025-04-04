package org.dpcq.ai.controller.admin;

import com.dpcq.base.annotation.AnonymousAccess;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.dpcq.ai.pojo.req.RobotConnectParam;
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
     * 机器人列表
     */
//    @PostMapping("list")
//    public IPage list(@Validated @RequestBody RobotConnectParam param) {
//        return robotService.list(param);
//    }
}
