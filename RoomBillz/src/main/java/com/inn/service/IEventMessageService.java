package com.inn.service;

import org.springframework.http.ResponseEntity;

import com.inn.dto.ResponseDto;
import com.inn.entity.EventMessage;

public interface IEventMessageService {

	public EventMessage findEarliestFailedEvent();

	public ResponseEntity<ResponseDto> updateEventMessageByStatus(Integer id,String status);

	public ResponseEntity<ResponseDto> sendFailedPurchaseOrderDetailToNotificationService();

}
