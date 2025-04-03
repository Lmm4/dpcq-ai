package org.dpcq.ai.socket.handler.dto.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerBassVo {
    /**
     * 玩家ID
     */
    private Long id;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String name;

    /**
     * 国家
     */
    private String country;

    /**
     * 头像框ID
     */
    private String headFrameID;

    /**
     * 精度
     */
    private Double lng;

    /**
     * 纬度
     */
    private Double lat;

    /**
     * 玩家IP
     */
    private String ip;

}
