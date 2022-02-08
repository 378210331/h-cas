package com.hsy.cas.properties;

import lombok.Data;

@Data
public class TicketProperties {

    /**
     * ticket存活时间，客户端需要在这个时间内兑换登录信息
     * Time in seconds that service tickets should be considered live in CAS server.
     */
    long timeToKillInSeconds = 20L;
}
