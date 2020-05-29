package com.ccy.mybatisdemo.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MybatisPlusConfig
 * @Description //TODO
 * @Author ccy
 * @Date 2020/5/29 18:18
 * @Version 1.0
 **/
@Configuration
public class MybatisPlusConfig {

    //增加分页拦截器方法
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
