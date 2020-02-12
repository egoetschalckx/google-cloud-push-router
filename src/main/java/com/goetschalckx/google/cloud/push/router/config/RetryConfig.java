package com.goetschalckx.google.cloud.push.router.config;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class RetryConfig {

    @Bean
    public RetryTemplate retryTemplate(){
        return new RetryTemplate();
    }

    @Bean
    public Faker faker() {
        return new Faker();
    }

}
