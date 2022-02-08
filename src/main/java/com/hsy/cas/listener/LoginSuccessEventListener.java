package com.hsy.cas.listener;

import com.hsy.cas.event.LoginSuccessEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoginSuccessEventListener implements ApplicationListener<LoginSuccessEvent> {
    @Override
    public void onApplicationEvent(LoginSuccessEvent loginSuccessEvent) {
        //可以在这写登录成功的处理逻辑
       log.info("用户:{}登录成功", loginSuccessEvent.getSysUser().getRealname());
    }
}
