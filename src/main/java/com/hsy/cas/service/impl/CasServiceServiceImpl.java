package com.hsy.cas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hsy.cas.constant.RedisPrefixConstant;
import com.hsy.cas.entity.CasService;
import com.hsy.cas.mapper.CasServiceMapper;
import com.hsy.cas.service.ICasServiceService;
import com.hsy.cas.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @Description: 单点登录服务端信息
 * @author： husiyi
 * @date：   2021-11-09
 * @version： V1.0
 */
@Service
public class CasServiceServiceImpl extends ServiceImpl<CasServiceMapper, CasService> implements ICasServiceService {

    @Autowired
    RedisUtil redisUtil;

    AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public List<CasService> listAllByOrder() {
        Object cache = redisUtil.get(RedisPrefixConstant.CAS_SERVICE);
        if(cache != null){
            return (List<CasService>) cache;
        }else{
            LambdaQueryWrapper<CasService> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CasService::getStatus,"1");
            queryWrapper.orderByAsc(CasService::getEvaluationOrder);
            List<CasService> casServiceList = getBaseMapper().selectList(queryWrapper);
            redisUtil.set(RedisPrefixConstant.CAS_SERVICE,casServiceList, 100L);
            return casServiceList;
        }
    }

    @Override
    public  CasService isMatch(String service){
        if(StringUtils.isNotBlank(service)){
            List<CasService> casServiceList = listAllByOrder();
            for(CasService s : casServiceList){
                try {
                    if (pathMatcher.match(s.getServiceid(), service)) {
                        return s;
                    } else if (Pattern.matches(s.getServiceid(), service)) {
                        return s;
                    }
                }catch (Exception e){
                    log.error("路径判断失败:{}",e);
                }
            }
        }
        return null;
    }
}
