package com.inn.client;

import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "ROOMBILLZ-NOTIFICATION-SERVICE", url = "${notification.service.url}",
             configuration = {FeignConfig.class,FeignRetryConfig.class})
public interface NotificationFeignClient {

}
