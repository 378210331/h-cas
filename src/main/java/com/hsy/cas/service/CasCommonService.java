package com.hsy.cas.service;

import com.auth0.jwt.interfaces.Claim;
import com.hsy.cas.constant.RedisPrefixConstant;
import com.hsy.cas.entity.SysUser;
import com.hsy.cas.model.UserInfoDto;
import com.hsy.cas.properties.CasProperties;
import com.hsy.cas.utils.JWTUtils;
import com.hsy.cas.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 单点登录基础服务
 */
@Service
public class CasCommonService {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    ISysUserService userService;

    @Autowired
    CasProperties casProperties;

    /**
     * 生成单点登录成功信息
     * @param user 登录用户信息
     * @return 返回给登录页面的登录信息项
     */
    public UserInfoDto generaUserInfo(SysUser user){
        UserInfoDto userInfoDto = new UserInfoDto();
        BeanUtils.copyProperties(user,userInfoDto);
        userInfoDto.setRoleId(StringUtils.join(userService.getRolesByUserId(user.getId()),","));
        long expire = casProperties.getTgt().getMaxTimeToLiveInSeconds();
        String token =  JWTUtils.createToken(userInfoDto, expire);
        redisUtil.set(RedisPrefixConstant.TOKEN_PREFIX + token, JWTUtils.createToken(userInfoDto, expire),expire);
        userInfoDto.setToken(token);
        userInfoDto.setTicket(createTicket(token));
        return userInfoDto;
    }

    /**
     * 根据已登录的TGT信息生成供客户端兑换的ticket
     * @param authorization
     * @return
     */
    public String createTicket(String authorization){
        if(StringUtils.isBlank(authorization)){
            return null;
        }else{
            Map<String, Claim> map =  JWTUtils.decodeToken(authorization);
            Map<String,String> userInfo = new HashMap<>();
            for(Map.Entry<String,Claim> entry : map.entrySet()){
                if(entry.getValue() != null){
                    userInfo.put(entry.getKey(),entry.getValue().asString());
                }
            }
            String ticket = "ST-" + UUID.randomUUID();
            long expire = casProperties.getSt().getTimeToKillInSeconds(); //需要在这个时间段内完成ticket的兑换
            redisUtil.set(RedisPrefixConstant.TGT_ST + ticket,authorization,expire);
            redisUtil.set(RedisPrefixConstant.TICKET_PREFIX + ticket,userInfo,expire);
            return ticket;
        }
    }
}
