package com.hsy.cas.controller;

import com.hsy.cas.constant.RedisPrefixConstant;
import com.hsy.cas.event.LogoutEvent;
import com.hsy.cas.model.Result;
import com.hsy.cas.utils.JWTUtils;
import com.hsy.cas.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LogoutController {


    @Autowired
    RedisUtil redisUtil;

    @Autowired
    ApplicationContext applicationContext;

    /**
     * 前往单点登出页面
     * @param request
     * @return
     */
    @GetMapping(value = "/logout")
    public ModelAndView logout(HttpServletRequest request) {
        String service = request.getParameter("service");
        ModelAndView mv = new ModelAndView("logout");
        mv.addObject("service",service);
        return mv;
    }


    /**
     * 处理登出逻辑
     * @param authorization
     * @return
     */
    @GetMapping(value = "/handleLogout")
    @ResponseBody
    public Result<Void> handleLogout(@RequestHeader(value = HttpHeaders.AUTHORIZATION,required = false) String authorization) {
        try{
            if(StringUtils.isNotBlank(authorization)){
                if(redisUtil.hasKey(RedisPrefixConstant.TOKEN_PREFIX + authorization)){
                    redisUtil.del(RedisPrefixConstant.TOKEN_PREFIX  + authorization);
                    String userId = JWTUtils.decodeToken(authorization).get("id").asString();
                    applicationContext.publishEvent(new LogoutEvent(this,userId,authorization));
                }
            }
        }catch (Exception e){
            return Result.buildError("登出失败");
        }
        return Result.buildSuccess("登出成功");
    }
}
