package com.hsy.cas.filter;


import com.hsy.cas.constant.RedisPrefixConstant;
import com.hsy.cas.properties.CasProperties;
import com.hsy.cas.utils.JWTUtils;
import com.hsy.cas.utils.RedisUtil;
import com.hsy.cas.utils.SpringContextUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 单应用权限拦截器
 */
@Slf4j
@Data
public class AccessFilter implements Filter {

    String contextPath;

    List<String> needAuthList ;

    CasProperties casProperties;


    private static final String UTF_8 = "UTF-8";
    private static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json;charset=UTF-8";

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("初始化AccessFilter");
        List<String> list = new ArrayList<>();
        list.add("/casServices");
        list.add("/casServices/**");
        this.needAuthList = list;
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String url = request.getRequestURI().substring(contextPath.length());
        PathMatcher pathMatcher = new AntPathMatcher();
        boolean needAuth = false;
        if(! url.endsWith("index")) {
            for (String pattern : needAuthList) {
                if (pathMatcher.match(pattern, url)) {
                    needAuth = true;
                    break;
                }
            }
        }
        if(! needAuth){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String authentication = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.isBlank(authentication)){
            unauthorized(httpResponse);
            return ;
        }
        RedisUtil redisUtil = SpringContextUtils.getBean(RedisUtil.class);
        if(! redisUtil.hasKey(RedisPrefixConstant.TOKEN_PREFIX + authentication)){
            unauthorized(httpResponse);
            return ;
        }
        Map<String, String> stringMap = JWTUtils.decodeTokenToMap(authentication);
        if(Arrays.stream(casProperties.getAdminIds()).anyMatch(s -> StringUtils.equals(s,stringMap.get("id")))){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }else{
            unauthorized(httpResponse);
            return ;
        }
    }

    @Override
    public void destroy() {
    	//暂不实现
    }

    private void unauthorized(HttpServletResponse httpResponse) throws IOException {
        httpResponse.sendRedirect("/cas/login");
    }

}
