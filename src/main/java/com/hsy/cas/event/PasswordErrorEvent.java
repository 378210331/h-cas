package com.hsy.cas.event;

import com.hsy.cas.entity.SysUser;
import org.springframework.context.ApplicationEvent;

public class PasswordErrorEvent  extends ApplicationEvent {


    SysUser sysUser;

    public PasswordErrorEvent(Object source, SysUser sysUser) {
        super(source);
        this.sysUser = sysUser;
    }

    public SysUser getSysUser(){
        return this.sysUser;
    }
}
