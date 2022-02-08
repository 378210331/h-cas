package com.hsy.cas.config;

import com.hsy.cas.properties.CasProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * cas 自动配置类
 */
@EnableConfigurationProperties(value = {CasProperties.class})
@Slf4j
public class CasAutoConfig {
}
