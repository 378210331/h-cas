package com.hsy.cas.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

/**
 *
 * @author husiyi
 */
@MapperScan(value = {"com.hsy.**.mapper*"})
public class MybatisPlusAutoConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

//    /**
//     * mybatis-plus SQL执行效率插件【生产环境可以关闭】
//     */
//    @Bean
//    public PerformanceInterceptor performanceInterceptor() {
//        return new PerformanceInterceptor();
//    }


    /**
     * 数据库自动适配
     */
    @Bean
    public DatabaseIdProvider databaseIdProvider() {
        VendorDatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        Properties properties = new Properties();
        properties.put("DM DBMS", "dameng");
        properties.put("MySQL", "mysql");
        properties.put("Oracle", "oracle");
        properties.put("KingbaseES", "kingbase");
        databaseIdProvider.setProperties(properties);
        return databaseIdProvider;
    }
}
