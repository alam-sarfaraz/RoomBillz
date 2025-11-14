package com.inn.controller.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.inn.controller.IUserGroupRegistrationController;
import com.inn.dto.ResponseDto;
import com.inn.dto.UserGroupRegistrationDto;
import com.inn.entity.UserGroupDetailMapping;
import com.inn.logs.LogRequestResponse;
import com.inn.roomConstants.RoomConstants;
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
			logger.info(RoomConstants.INSIDE_THE_METHOD + "registerUserWithGroup {}", kv("UserGroupRegistrationDto",userGroupRegistrationDto));
			return iUserGroupRegistrationService.registerUserWithGroup(userGroupRegistrationDto);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO,kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	@LogRequestResponse
	public ResponseEntity<UserGroupDetailMapping> findUserGroupDetailByUserAndGroupName(String userName,String groupName) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "findUserGroupDetailByUserAndGroupName {}", kv("userName",userName), kv("groupName",groupName));
			return iUserGroupRegistrationService.findUserGroupDetailByUserAndGroupName(userName,groupName);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO,kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	@LogRequestResponse
	public ResponseEntity<List<UserGroupDetailMapping>> findUserGroupDetailByUsername(String userName) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "findUserGroupDetailByUsername {}", kv("UserName",userName));
			return iUserGroupRegistrationService.findUserGroupDetailByUsername(userName);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO,kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	@LogRequestResponse
	public ResponseEntity<List<UserGroupDetailMapping>> findUserGroupDetailByGroupName(String groupName) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "findUserGroupDetailByGroupName {}",kv("GroupName",groupName));
			return iUserGroupRegistrationService.findUserGroupDetailByGroupName(groupName);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO,kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	@LogRequestResponse
	public ResponseEntity<List<UserGroupDetailMapping>> findUserGroupDetailByEmail(String email) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "findUserGroupDetailByEmail {}",kv("Email",email));
			return iUserGroupRegistrationService.findUserGroupDetailByEmail(email);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO,kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	@LogRequestResponse
	public ResponseEntity<byte[]> exportUserGroupDetailMappingByGroupName(String groupName) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "exportUserGroupDetailMappingByGroupName {}",kv("GroupName",groupName));
			return iUserGroupRegistrationService.exportUserGroupDetailMappingByGroupName(groupName);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO,kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	@LogRequestResponse
	public ResponseEntity<byte[]> exportUserGroupDetailMappingByUsername(String username) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "exportUserGroupDetailMappingByUsername {}",kv("Username",username));
			return iUserGroupRegistrationService.exportUserGroupDetailMappingByUsername(username);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO,kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	@LogRequestResponse
	public ResponseEntity<List<String>> getUserListByGroupName(String groupName) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "getUserListByGroupName {}",kv("GroupName",groupName));
			return iUserGroupRegistrationService.getUserListByGroupName(groupName);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO,kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	@LogRequestResponse
	public ResponseEntity<List<String>> getEmailListByGroupName(String groupName) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "getEmailListByGroupName {}",kv("GroupName",groupName));
			return iUserGroupRegistrationService.getEmailListByGroupName(groupName);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO,kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

}
