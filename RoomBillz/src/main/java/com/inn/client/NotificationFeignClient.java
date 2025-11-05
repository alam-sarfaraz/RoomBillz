package com.inn.client;

import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "ROOMBILLZ-NOTIFICATION-SERVICE",configuration = {FeignConfig.class,FeignRetryConfig.class})
public interface NotificationFeignClient {

}
