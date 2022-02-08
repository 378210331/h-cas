package com.hsy.cas.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.hsy.cas.model.UserInfoDto;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {

    private   static final JWTCreator.Builder builder = JWT.create();

    private static final String SECRET = "hsy";

    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET);

    public static String createToken(Map<String,String> attribute,long expireInSecond){
        if(attribute == null){
            attribute = new HashMap<>();
        }
        builder.withExpiresAt(new Date(System.currentTimeMillis() + expireInSecond));
        for(Map.Entry<String,String> entry : attribute.entrySet()){
            builder.withClaim(entry.getKey(),entry.getValue());
        }
        return builder.sign(algorithm);
    }

    public static Map<String, Claim> decodeToken(String token){
       return  JWT.require(algorithm).build().verify(token).getClaims();
    }

    public static Map<String, String> decodeTokenToMap(String token){
        Map<String, Claim> claimMap =  decodeToken(token);
        Map<String,String> userInfo = new HashMap<>();
        for(Map.Entry<String,Claim> entry : claimMap.entrySet()){
            if(entry.getValue() != null){
                userInfo.put(entry.getKey(),entry.getValue().asString());
            }
        }
        return userInfo;
    }

    public static String createToken(UserInfoDto userInfoDto, long expireInSecond){
        builder.withExpiresAt(new Date(System.currentTimeMillis() + expireInSecond * 1000));
        builder.withClaim("id",userInfoDto.getId());
        builder.withClaim("realname",userInfoDto.getRealname());
        builder.withClaim("depId",userInfoDto.getDepId());
        builder.withClaim("username",userInfoDto.getUsername());
        builder.withClaim("roleId",userInfoDto.getRoleId());
        return builder.sign(algorithm);
    }

}
