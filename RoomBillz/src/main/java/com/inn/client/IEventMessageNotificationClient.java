package com.inn.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.inn.dto.EventMessageCreateRequestDto;
import com.inn.dto.ResponseDto;

@FeignClient(name = "ROOMBILLZ-NOTIFICATION-SERVICE", url = "${notification.service.url}", path = "/eventMessage", configuration = { FeignConfig.class, FeignRetryConfig.class })
public interface IEventMessageNotificationClient {

	@PostMapping(path = "/createEventMessage", consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<ResponseDto> createEvent(@RequestBody EventMessageCreateRequestDto eventMessageCreateRequestDto);
}
