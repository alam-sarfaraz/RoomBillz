package com.inn.client;

import static java.util.concurrent.TimeUnit.SECONDS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Retryer;

@Configuration
public class FeignRetryConfig {

    @Bean
    public Retryer retryer() {
        // Retry 3 times, starting at 1s, with max interval of 5s
        return new Retryer.Default(1000, SECONDS.toMillis(5), 3);
    }
}