package com.hsy.cas.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfoDto implements Serializable {

    private String id;
    private String depId;
    private String username;
    private String realname;
    private String roleId;
    private String token ;
    private String ticket;

}
