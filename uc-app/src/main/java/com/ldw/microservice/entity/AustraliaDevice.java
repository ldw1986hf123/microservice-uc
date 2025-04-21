package com.ldw.microservice.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 澳大利亚设备
 * @author lujq
 * @time 2024/2/19 10:05
 */
@Data
@TableName("australia_device")
public class AustraliaDevice     {
    // 设备SN号
    private String sn;

    // lfdi
    private String lfdi;

    // sfdi
    private String sfdi;

    private String serialNum;

    private String nmi;

    // 是否三相,1是,0否
    private Integer threePhase;

    // 是否已经注册, 1是,0否
    private Integer registered;

    // 是否已经为其创建edev，1是，0否
    private Integer created;
    private String powerGrid;
}
