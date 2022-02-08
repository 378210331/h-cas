package com.hsy.cas.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.io.Serializable;

@Data
@ConfigurationProperties(prefix = "cas")
public class CasProperties implements Serializable {

    /**
     * 超级管理员用户id
     */
    String [] adminIds = {};

    @NestedConfigurationProperty
    TGCProperties tgc;

    @NestedConfigurationProperty
    TGTProperties tgt;

    @NestedConfigurationProperty
    TicketProperties st;

}
