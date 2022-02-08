package com.hsy.cas.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * TGt配置类
 * 参考cas: org.apereo.cas.configuration.model.core.ticket.TicketGrantingTicketProperties
 */
@Data
public class TGTProperties implements Serializable {

    /**
     * 服务端TGT存储时间,决定浏览器cookies中的TGT有效时间,有效期间同意浏览器客户端向cas获取ticket不需要登录
     * Maximum time in seconds TGTs would be live in CAS server.
     */
    long maxTimeToLiveInSeconds = 2 * 3600L;
}
