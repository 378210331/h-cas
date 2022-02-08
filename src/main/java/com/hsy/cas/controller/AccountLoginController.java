package com.hsy.cas.controller;

import com.hsy.cas.entity.SysUser;
import com.hsy.cas.event.PasswordErrorEvent;
import com.hsy.cas.event.UserNotFoundEvent;
import com.hsy.cas.model.Result;
import com.hsy.cas.model.UserInfoDto;
import com.hsy.cas.properties.CasProperties;
import com.hsy.cas.service.CasCommonService;
import com.hsy.cas.service.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AccountLoginController {

    @Autowired
    ISysUserService userService;

    @Autowired
    ApplicationContext applicationContext;


    @Autowired
    CasCommonService casCommonService;

    @Autowired
    CasProperties casProperties;


    @PostMapping("/accountLogin")
    public Result<UserInfoDto> accountLogin(@RequestParam("username") String username,
                                            @RequestParam("password") String password,
                                            HttpServletRequest request) {
        SysUser user = userService.getUserByName(username);
        if (user == null) {
            applicationContext.publishEvent(new UserNotFoundEvent(this, username));
            return Result.buildError("用户不存在或者密码错误");
        }
        if (StringUtils.equals(user.getPassword(), password)) {
            UserInfoDto userInfoDto = casCommonService.generaUserInfo(user);
            return Result.buildSuccess("登录成功", userInfoDto);
        } else {
            applicationContext.publishEvent(new PasswordErrorEvent(this, user));
            return Result.buildError("用户不存在或者密码错误");
        }
    }

}
