package com.hsy.cas.event;

import com.hsy.cas.entity.SysUser;
import org.springframework.context.ApplicationEvent;

public class LoginSuccessEvent extends ApplicationEvent {

    SysUser sysUser;

    public LoginSuccessEvent(Object source, SysUser sysUser) {
        super(source);
        this.sysUser = sysUser;
    }

    public SysUser getSysUser(){
        return this.sysUser;
    }
}
