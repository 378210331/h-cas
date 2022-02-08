package com.hsy.cas.listener;

import cn.hutool.http.HttpRequest;
import com.hsy.cas.constant.RedisPrefixConstant;
import com.hsy.cas.entity.CasService;
import com.hsy.cas.event.LogoutEvent;
import com.hsy.cas.service.ICasServiceService;
import com.hsy.cas.utils.ISOStandardDateFormat;
import com.hsy.cas.utils.RedisUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

/**
 * 统一登出处理，登出所有使用该TGT登出的客户端
 */
@Slf4j
@Component
public class LogoutEventListener implements ApplicationListener<LogoutEvent> {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    ICasServiceService casServiceService;

    private static final String LOGOUT_REQUEST_TEMPLATE =
            "<samlp:LogoutRequest xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\" ID=\"%s\" Version=\"2.0\" "
                    + "IssueInstant=\"%s\"><saml:NameID xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\">@NOT_USED@"
                    + "</saml:NameID><samlp:SessionIndex>%s</samlp:SessionIndex></samlp:LogoutRequest>";

    @SneakyThrows
    @Override
    @Async
    public void onApplicationEvent(LogoutEvent logoutEvent) {
        String TGT = logoutEvent.getTgt();
        Map<Object,Object> clientInfo = redisUtil.hmGet(RedisPrefixConstant.TGT_ST_SERVICE + TGT);
        redisUtil.del(RedisPrefixConstant.TGT_ST_SERVICE + TGT);
        if(clientInfo == null ) return ;
        for(Map.Entry<Object,Object> entry : clientInfo.entrySet()){
            CasService casService = casServiceService.getById((Serializable) entry.getKey()); //获取登录过的客户端
            if(casService != null && StringUtils.isNotBlank(casService.getLogoutUrl())){
              String logoutRequest = String.format(LOGOUT_REQUEST_TEMPLATE, "LR-2" + UUID.randomUUID(),
                new ISOStandardDateFormat().getCurrentDateAndTime(), entry.getValue());
                 logoutRequest = "logoutRequest=" + URLEncoder.encode(logoutRequest, "UTF-8");
                 try {
                     HttpRequest.post(casService.getLogoutUrl()).timeout(5000).body(logoutRequest).execute(); //发送登出请求
                 }catch (Exception e){
                     log.error("执行子系统:{},登出失败:{},登出路径为:{}",casService.getName(),e.getMessage(),casService.getLogoutUrl());
                 }
            }
        }
    }
}
