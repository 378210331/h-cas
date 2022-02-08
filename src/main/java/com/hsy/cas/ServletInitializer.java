package com.hsy.cas;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * war包发布提供servlet支持
 */
@Slf4j
public class ServletInitializer extends SpringBootServletInitializer implements WebApplicationInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CasApplication.class);
    }

    @Override
    protected WebApplicationContext run(SpringApplication application) {
        ConfigurableApplicationContext configurableApplicationContext = application.run();
        Environment env = configurableApplicationContext.getEnvironment();
        String ip = "";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {

        }
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
        return (WebApplicationContext)configurableApplicationContext;
    }
}
