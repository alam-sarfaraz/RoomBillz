package com.inn.service.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inn.dto.ResponseDto;
import com.inn.entity.EventMessage;
import com.inn.repository.IEventMessageRepository;
import com.inn.roomConstants.RoomConstants;
import com.inn.service.IEventMessageService;

@Service
public class EventMessageServiceImpl implements IEventMessageService {
	
	private static final Logger logger = LoggerFactory.getLogger(EventMessageServiceImpl.class);
	
	@Autowired
	IEventMessageRepository eventMessageRepository;

	@Override
	public EventMessage findEarliestFailedEvent() {
		logger.info(RoomConstants.INSIDE_THE_METHOD +"findEarliestFailedEvent");
		try {
			return eventMessageRepository.findFirstByStatusOrderByCreatedAtAsc("Failed").orElse(null);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	public ResponseEntity<ResponseDto> updateEventMessageByStatus(Integer id, String status) {
		logger.info(RoomConstants.INSIDE_THE_METHOD + "updateEventMessageByStatus {}", kv("ID", id), kv("status", status));
	    try {
	        return eventMessageRepository.findById(id)
	                .map(eventMessage -> {
	                    eventMessage.setStatus(status);
	                    eventMessageRepository.save(eventMessage);
	                    return ResponseEntity.ok(new ResponseDto("200", "EventMessage status updated successfully."));
	                })
	                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
	                        .body(new ResponseDto("404", "EventMessage not found with id: " + id)));

	    } catch (Exception e) {
	        logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()), e);
	        throw e;
	    }
	}

	@Override
	public ResponseEntity<ResponseDto> sendFailedPurchaseOrderDetailToNotificationService() {
		logger.info(RoomConstants.INSIDE_THE_METHOD + "sendFailedPurchaseOrderDetailToNotificationService");
	    try {
	        EventMessage earliestFailedEvent = this.findEarliestFailedEvent();
	        if (earliestFailedEvent == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(new ResponseDto("404", "No failed EventMessage found to send."));
	        }

	        Integer id = earliestFailedEvent.getId();
	        this.updateEventMessageByStatus(id, "Success");
	        return ResponseEntity.ok(new ResponseDto("200", "EventMessage sent to Notification Service successfully."));
	    } catch (Exception e) {
	        logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()), e);
	        throw e;
	    }
	}



}
