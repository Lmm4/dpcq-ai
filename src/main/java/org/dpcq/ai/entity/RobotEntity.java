package org.dpcq.ai.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dpcq.base.entity.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("robot")
public class RobotEntity extends BaseEntity{

    private Long userId;
    /**
     * 风格
     */
    private Integer characters;
    /**
     * 筹码不足时是否补充带入
     */
    private boolean supplement;
    /**
     * 状态
     */
    private Integer status;
}
