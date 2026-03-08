package com.inn.controller.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.inn.controller.ILoginRequestController;
import com.inn.dto.AuthResponse;
import com.inn.dto.LoginRequest;
import com.inn.logs.LogRequestResponse;
import com.inn.roomConstants.RoomConstants;
import com.inn.service.ILoginRequestService;

import jakarta.validation.Valid;

@RestController
public class LoginRequestControllerImpl implements ILoginRequestController{
	
	private static final Logger logger = LoggerFactory.getLogger(LoginRequestControllerImpl.class);
	
	@Autowired
	ILoginRequestService iLoginRequestService;

	@Override
	@LogRequestResponse
	public ResponseEntity<AuthResponse> login(@Valid LoginRequest loginRequest) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "login :",kv("LoginRequest",loginRequest));
			return iLoginRequestService.login(loginRequest);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

}
