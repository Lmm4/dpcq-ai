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
@EqualsAndHashCode(callSuper = true)
@TableName("robot")
public class RobotEntity extends BaseEntity{

    private Long userId;
    /**
     * 风格
     */
    private Integer characters;
    /**
     * 状态
     */
    private Integer status;
}
