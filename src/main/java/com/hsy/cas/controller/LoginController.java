package com.hsy.cas.controller;

import com.hsy.cas.constant.RedisPrefixConstant;
import com.hsy.cas.model.Result;
import com.hsy.cas.properties.CasProperties;
import com.hsy.cas.service.ICasServiceService;
import com.hsy.cas.utils.JWTUtils;
import com.hsy.cas.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    ICasServiceService casServiceService;

    @Autowired
    CasProperties casProperties;

    @GetMapping("/")
    public ModelAndView index(){
        return new ModelAndView("redirect:/login");
    }

    /**
     * 前往单点登录服务页面
     * @param request
     * @return
     */
    @GetMapping(value = "/login")
    public ModelAndView login(HttpServletRequest request) {
        String service = request.getParameter("service");
        if(casServiceService.isMatch(service) != null || StringUtils.isBlank(service)){
            ModelAndView mv = new ModelAndView("login");
            mv.addObject("service",service);
            return mv;
        }else{
            ModelAndView mv = new ModelAndView("errorService");
            mv.addObject("service",service);
            return mv;
        }
    }

    /**
     * 前往默认登录成功页面
     * @param request
     * @return
     */
    @GetMapping(value = "/defaultSuccess")
    public ModelAndView defaultSuccess(HttpServletRequest request) {
        return new  ModelAndView("defaultSuccess");
    }


    /**
     * 前往默认登录成功页面
     * @return
     */
    @GetMapping(value = "/tgcValidate")
    @ResponseBody
    public Result<Map<String,String>> tgcValidate(@RequestHeader(value = HttpHeaders.AUTHORIZATION,required = false) String token) {
        if(StringUtils.isBlank(token)){
            return Result.buildError("未登录");
        }
        if(! redisUtil.hasKey(RedisPrefixConstant.TOKEN_PREFIX + token)){
            return Result.buildError("未登录");
        }else {
            Map<String, String> stringMap = JWTUtils.decodeTokenToMap(token);
            if(Arrays.stream(casProperties.getAdminIds()).anyMatch(s -> StringUtils.equals(s,stringMap.get("id")))){
                stringMap.put("isAdmin","true");
            }
            return Result.buildSuccess("查询成功", stringMap);
        }
    }

}
