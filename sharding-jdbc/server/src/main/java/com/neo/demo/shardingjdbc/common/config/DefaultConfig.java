package com.neo.demo.shardingjdbc.common.config;

import com.neo.http.server.filter.HttpFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultConfig {

    @Bean
    public HttpFilter httpFilter() {
        return new HttpFilter();
    }
}
