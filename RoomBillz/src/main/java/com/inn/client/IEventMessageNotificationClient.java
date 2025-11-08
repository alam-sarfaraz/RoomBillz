package com.inn.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.inn.dto.EventMessageCreateRequestDto;
import com.inn.dto.ResponseDto;

@FeignClient(name = "ROOMBILLZ-NOTIFICATION-SERVICE", path = "/api/v1/notification/eventMessage",configuration = {FeignConfig.class,FeignRetryConfig.class})
public interface IEventMessageNotificationClient {

	  @PostMapping(path = "/createEventMessage", consumes = {MediaType.APPLICATION_JSON_VALUE})
	  public ResponseEntity<ResponseDto> createEvent(@RequestBody EventMessageCreateRequestDto eventMessageCreateRequestDto);
}
