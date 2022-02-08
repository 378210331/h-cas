package com.hsy.cas;


import com.hsy.cas.filter.AccessFilter;
import com.hsy.cas.properties.CasProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author husiyi
 * 2021.11.06
 */

@SpringBootApplication(scanBasePackages = {"com.hsy"})
@EnableAsync
@Slf4j
public class CasApplication {

    public static void main(String[] args) throws UnknownHostException {
        //System.setProperty("spring.cloud.bootstrap.enabled","true");
        SpringApplication springApplication = new SpringApplication(CasApplication.class);
        ConfigurableApplicationContext application = springApplication.run(args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String applicationName = env.getProperty("spring.application.name");
        String path = StringUtils.defaultIfEmpty(env.getProperty("server.servlet.context-path"),"");
        log.info("\n----------------------------------------------------------\n\t" +
                        "Application {} is running! Access URLs:\n\t" +
                        "Local: \t\thttp://localhost:{}{}/\n\t" +
                        "External: \thttp://{}:{}{}/\n\t" +
                        "Doc: \t\thttp://{}:{}{}/doc.html\n" +
                        "----------------------------------------------------------"
                ,applicationName ,port,path,ip,port,path,ip,port,path);
    }

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Autowired
    CasProperties casProperties;

    @Bean
    public FilterRegistrationBean<AccessFilter> accessFilter() {
        //通过FilterRegistrationBean实例设置优先级可以生效
        FilterRegistrationBean<AccessFilter> bean = new FilterRegistrationBean<AccessFilter>();
        AccessFilter filter = new AccessFilter();
        filter.setContextPath(contextPath);
        filter.setCasProperties(casProperties);
        bean.setFilter(filter);//注册自定义过滤器
        bean.setName("accessFilter");//过滤器名称
        bean.addUrlPatterns("/*");//过滤所有路径
        bean.setOrder(1);//优先级，最顶级
        return bean;
    }

}
