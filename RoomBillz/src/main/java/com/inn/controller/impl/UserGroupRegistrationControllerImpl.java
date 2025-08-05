package com.inn.controller.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.inn.controller.IUserGroupRegistrationController;
import com.inn.dto.ResponseDto;
import com.inn.dto.UserGroupRegistrationDto;
import com.inn.logs.LogRequestResponse;
import com.inn.roomConstants.RoomContants;
import com.inn.service.IUserGroupRegistrationService;

@RestController
public class UserGroupRegistrationControllerImpl implements IUserGroupRegistrationController{
	
	private static final Logger logger = LoggerFactory.getLogger(UserGroupRegistrationControllerImpl.class);
	
	@Autowired
	IUserGroupRegistrationService iUserGroupRegistrationService;
	
	@Override
	@LogRequestResponse
	public ResponseEntity<ResponseDto> registerUserWithGroup(UserGroupRegistrationDto userGroupRegistrationDto) {
		try {
			logger.info(RoomContants.INSIDE_THE_METHOD + "registerUserWithGroup {}", kv("UserGroupRegistrationDto",userGroupRegistrationDto));
			return iUserGroupRegistrationService.registerUserWithGroup(userGroupRegistrationDto);
		} catch (Exception e) {
			logger.error(RoomContants.ERROR_OCCURRED_DUE_TO,kv("Error Message", e.getMessage()));
			throw e;
		}
	}

}
