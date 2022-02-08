package com.hsy.cas.event;

import org.springframework.context.ApplicationEvent;

public class UserNotFoundEvent  extends ApplicationEvent {

    String username;

    public UserNotFoundEvent(Object source, String  username) {
        super(source);
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }
}
