package org.dpcq.ai.repo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dpcq.ai.entity.RobotEntity;
import org.dpcq.ai.mapper.RobotMapper;
import org.dpcq.ai.repo.IRobotRepo;
import org.springframework.stereotype.Service;

@Service
public class RobotRepo extends ServiceImpl<RobotMapper, RobotEntity> implements IRobotRepo {
}
