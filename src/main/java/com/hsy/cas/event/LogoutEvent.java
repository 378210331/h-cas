package com.hsy.cas.event;

import org.springframework.context.ApplicationEvent;

public class LogoutEvent extends ApplicationEvent {

    String userId;

    String tgt;

    public LogoutEvent(Object source,String userId,String tgt) {
        super(source);
        this.userId = userId;
        this.tgt = tgt;
    }

    public String getUserId() {
        return userId;
    }

    public String getTgt(){
        return tgt;
    }
}
