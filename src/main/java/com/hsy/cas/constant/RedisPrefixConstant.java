package com.hsy.cas.constant;

public class RedisPrefixConstant {

    /**
     * Service Ticket
     * 一次性使用ticket存储
     */
    public static final String TICKET_PREFIX = "ST::";

    /**
     * Ticket Granting Ticket
     */
    public static final String TOKEN_PREFIX = "TGT::";


    public static final String TGT_ST = "TGT_ST::";
    /**
     * 关联关系
     */
    public static final String TGT_ST_SERVICE = "TGT_ST_SERVICE::";

    public static final String CAS_SERVICE = "CAS_SERVICE";

}
