package com.inn.controller.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.inn.controller.IUserRegistrationController;
import com.inn.dto.ResponseDto;
import com.inn.dto.UserRegistrationDto;
import com.inn.entity.UserRegistration;
import com.inn.logs.LogRequestResponse;
import com.inn.roomConstants.RoomContants;
import com.inn.service.IUserRegistrationService;

@RestController
public class UserRegistationControllerImpl implements IUserRegistrationController{
	
	private static final Logger logger = LoggerFactory.getLogger(UserRegistationControllerImpl.class);
	
	@Autowired
	IUserRegistrationService iUserRegistrationService;
	
	@Override
	@LogRequestResponse
	public ResponseEntity<ResponseDto> userRegistration(UserRegistrationDto userRegistrationDto) {
		try {
			logger.info(RoomContants.INSIDE_THE_METHOD + "userRegistration {}", kv("UserRegistrationDto",userRegistrationDto));
			return iUserRegistrationService.userRegistration(userRegistrationDto);
		} catch (Exception e) {
			logger.error(RoomContants.ERROR_OCCURRED_DUE_TO,kv("Error Message", e.getMessage()));
			throw e;
		}
	}
	
	  @Override
	  @LogRequestResponse
	  public ResponseEntity<UserRegistration> findById(Integer id) {
		  try {
		  logger.info(RoomContants.INSIDE_THE_METHOD + "findById {}",kv("Id",id));
		  UserRegistration userRegistation = iUserRegistrationService.findById(id);
	         return ResponseEntity.status(HttpStatus.OK)
	 				.body(userRegistation);
	  }catch (Exception e) {
		  logger.error(RoomContants.ERROR_OCCURRED_DUE_TO,kv("Error Message", e.getMessage()));
			throw e;
		}
	  }
	  
	  @Override
	  @LogRequestResponse
	  public ResponseEntity<UserRegistration> findByUserName(String userName) {
			try {
				logger.info(RoomContants.INSIDE_THE_METHOD + "findByUserName {}",kv("UserName", userName));
				UserRegistration userRegistation = iUserRegistrationService.findByUserName(userName);
				return ResponseEntity.status(HttpStatus.OK).body(userRegistation);
			} catch (Exception e) {
				logger.error(RoomContants.ERROR_OCCURRED_DUE_TO,kv("Error Message", e.getMessage()));
				throw e;
			}
		}
	  
		@Override
		@LogRequestResponse
		public ResponseEntity<ResponseDto> deleteUserById(Integer id) {
			try {
				logger.info(RoomContants.INSIDE_THE_METHOD + "deleteUserById {}",kv("Id:", id));
				return iUserRegistrationService.deleteUserById(id); 
			} catch (Exception e) {
				logger.error(RoomContants.ERROR_OCCURRED_DUE_TO,kv("Error Message", e.getMessage()));
				throw e;
			}
		}


		@Override
		@LogRequestResponse
		public ResponseEntity<ResponseDto> deleteByUserName(String userName) {
			try {
				logger.info(RoomContants.INSIDE_THE_METHOD + "deleteByUserName {}",kv("UserName", userName));
				return iUserRegistrationService.deleteByUserName(userName);
			} catch (Exception e) {
				logger.error(RoomContants.ERROR_OCCURRED_DUE_TO,kv("Error Message", e.getMessage()));
				throw e;
			}
		}

		@Override
		@LogRequestResponse
		public ResponseEntity<ResponseDto> updateUserByUserName(String userName,UserRegistrationDto userRegistrationDto) {
			try {
				logger.info(RoomContants.INSIDE_THE_METHOD + "updateUserByUserName {}", kv("UserName",userName),kv("UserRegistrationDto",userRegistrationDto));
				return iUserRegistrationService.updateUserByUserName(userName,userRegistrationDto);
			} catch (Exception e) {
				logger.error(RoomContants.ERROR_OCCURRED_DUE_TO,kv("Error Message", e.getMessage()));
				throw e;
			}
		}
		
		@Override
		@LogRequestResponse
		public ResponseEntity<UserRegistration> findUserDetailByUserId(String userId) {
			try {
				logger.info(RoomContants.INSIDE_THE_METHOD + "findUserDetailByUserId {}", kv("userId", userId));
				UserRegistration userRegistation = iUserRegistrationService.findUserDetailByUserId(userId);
				return ResponseEntity.status(HttpStatus.OK).body(userRegistation);
			} catch (Exception e) {
				logger.error(RoomContants.ERROR_OCCURRED_DUE_TO, kv("Error Message", e.getMessage()));
				throw e;
			}
		}

		@Override
		@LogRequestResponse
		public ResponseEntity<List<UserRegistration>> findAllUserDetil() {
			try {
				logger.info(RoomContants.INSIDE_THE_METHOD + "findAllUserDetil ");
				return iUserRegistrationService.findAllUserDetil();
			} catch (Exception e) {
				logger.error(RoomContants.ERROR_OCCURRED_DUE_TO, kv("Error Message", e.getMessage()));
				throw e;
			}
		}

		@Override
		@LogRequestResponse
		public ResponseEntity<UserRegistration> findUserDetailByEmail(String email) {
			try {
				logger.info(RoomContants.INSIDE_THE_METHOD + "findUserDetailByEmail {}",kv("Email",email));
				return iUserRegistrationService.findUserDetailByEmail(email);
			} catch (Exception e) {
				logger.error(RoomContants.ERROR_OCCURRED_DUE_TO, kv("Error Message", e.getMessage()));
				throw e;
			}
		}
	
 }
