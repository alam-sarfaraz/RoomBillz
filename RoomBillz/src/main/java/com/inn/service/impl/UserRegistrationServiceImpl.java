package com.inn.service.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inn.customException.UserAlreadyExistException;
import com.inn.customException.UserNotFoundException;
import com.inn.dto.ResponseDto;
import com.inn.dto.UserRegistrationDto;
import com.inn.entity.UserRegistration;
import com.inn.repository.IUserRegistrationRepository;
import com.inn.roomConstants.RoomContants;
import com.inn.roomUtility.RoomUtility;
import com.inn.service.IUserRegistrationService;

@Service
public class UserRegistrationServiceImpl implements IUserRegistrationService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserRegistrationServiceImpl.class);
	
	@Autowired
	IUserRegistrationRepository iUserRegistrationRepository;
	
	@Override
	public ResponseEntity<ResponseDto> userRegistration(UserRegistrationDto userRegistrationDto) {
		try {
			logger.info(RoomContants.INSIDE_THE_METHOD + "userRegistration",kv("UserRegistrationDto",userRegistrationDto));
			Optional<UserRegistration> userDetail = iUserRegistrationRepository.findByUserName(userRegistrationDto.getUserName());
			if(userDetail.isPresent()) {
			throw new UserAlreadyExistException("User with username " + userRegistrationDto.getUserName() + " already exists");
			}
			RoomUtility.validateEmail(userRegistrationDto.getEmail(), userRegistrationDto.getConfirmEmail());
			RoomUtility.validatePassword(userRegistrationDto.getPassword(), userRegistrationDto.getConfirmPassword());
			UserRegistration userRegistrationDb = mapToUserRegistration(userRegistrationDto,new UserRegistration());
			iUserRegistrationRepository.save(userRegistrationDb);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new ResponseDto("201", "UserRegistration Successfully..."));
		} catch (Exception e) {
			logger.error(RoomContants.ERROR_OCCURRED_DUE_TO,kv("Error Message", e.getMessage()));
			throw e;
		}
	}
	
	public UserRegistration mapToUserRegistration(UserRegistrationDto userRegistrationDto,UserRegistration userRegistration){
		logger.info(RoomContants.INSIDE_THE_METHOD + "mapToUserRegistration {}",kv("UserRegistrationDto",userRegistrationDto));
		userRegistration.setUserName(userRegistrationDto.getUserName());
		userRegistration.setUserId(RoomUtility.generateUserId(userRegistrationDto.getFirstName(), userRegistrationDto.getLastName()));
		userRegistration.setFirstName(userRegistrationDto.getFirstName());
		userRegistration.setMiddleName(userRegistrationDto.getMiddleName());
		userRegistration.setLastName(userRegistrationDto.getLastName());
		userRegistration.setEmail(userRegistrationDto.getEmail());
		userRegistration.setConfirmEmail(userRegistrationDto.getConfirmEmail());
		userRegistration.setPassword(userRegistrationDto.getPassword());
		userRegistration.setConfirmPassword(userRegistrationDto.getConfirmPassword());
		userRegistration.setDob(userRegistrationDto.getDob());
		userRegistration.setGender(userRegistrationDto.getGender());
		userRegistration.setMobileNumber(userRegistrationDto.getMobileNumber());
		userRegistration.setAtlMobileNumber(userRegistrationDto.getAtlMobileNumber());
		userRegistration.setIsActive(Boolean.TRUE);
		return userRegistration;
	}

	@Override
	public UserRegistration findById(Integer id) {
		try {
		logger.info(RoomContants.INSIDE_THE_METHOD + "findById {}",kv("Id",id));
		UserRegistration userRegistration = iUserRegistrationRepository.findById(id).orElseThrow(()->new UserNotFoundException("User", "Id", id.toString()));
		return userRegistration;
		}catch (Exception e) {
			logger.error(RoomContants.ERROR_OCCURRED_DUE_TO,kv("Error Message", e.getMessage()));
			throw e;
		}
	}
	
	@Override
	public UserRegistration findByUserName(String userName) {
		try {
			logger.info(RoomContants.INSIDE_THE_METHOD + "findByUserName {}",kv("UserName",userName));
			return iUserRegistrationRepository.findByUserName(userName)
					                          .orElseThrow(()->new UserNotFoundException("User", "UserName", userName));
			}catch (Exception e) {
				logger.error(RoomContants.ERROR_OCCURRED_DUE_TO,kv("Error Message", e.getMessage()));
				throw e;
			}
		}

	
		@Override
		public ResponseEntity<ResponseDto> deleteUserById(Integer id) {
			logger.info(RoomContants.INSIDE_THE_METHOD + "deleteUserById {}",kv("Id",id));
			try {
				if (!iUserRegistrationRepository.existsById(id)) {
					throw new UserNotFoundException("User", "Id", id.toString());
				}
				iUserRegistrationRepository.deleteById(id);
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseDto("200", "User Deleted Successfully..."));
			} catch (Exception e) {
				logger.error(RoomContants.ERROR_OCCURRED_DUE_TO,kv("Error Message", e.getMessage()));
				throw e;
			}
		}

		@Override
		public ResponseEntity<ResponseDto> deleteByUserName(String userName) {
			logger.info(RoomContants.INSIDE_THE_METHOD + "deleteByUserName {}",kv("UserName",userName));
			try {
				UserRegistration userRegistration = iUserRegistrationRepository.findByUserName(userName).orElseThrow(()->new UserNotFoundException("User", "UserName", userName));
				iUserRegistrationRepository.deleteById(userRegistration.getId());
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseDto("200", "User Deleted Successfully..."));
			} catch (Exception e) {
				logger.error(RoomContants.ERROR_OCCURRED_DUE_TO,kv("Error Message", e.getMessage()));
				throw e;
			}
		}

		@Override
		public ResponseEntity<ResponseDto> updateUserByUserName(String userName,UserRegistrationDto userRegistrationDto) {
			try {
				logger.info(RoomContants.INSIDE_THE_METHOD + "updateUserByUserName {}",kv("UserName",userName),kv("UserRegistrationDto",userRegistrationDto));
				UserRegistration userRegistration = iUserRegistrationRepository.findByUserName(userName)
						                          .orElseThrow(()->new UserNotFoundException("User", "UserName", userName));
				UserRegistration mapToUserRegistration = mapToUserRegistration(userRegistrationDto,userRegistration);
				iUserRegistrationRepository.save(mapToUserRegistration);
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(new ResponseDto("200", "User Detail Updated Successfully..."));
				}catch (Exception e) {
					logger.error(RoomContants.ERROR_OCCURRED_DUE_TO,kv("Error Message", e.getMessage()));
					throw e;
				}
			}
		
		public UserRegistration findUserDetailByUserId(String userId) {
			try {
			logger.info(RoomContants.INSIDE_THE_METHOD + "findUserDetailByUserId {}",kv("userId",userId));
			UserRegistration userRegistration = iUserRegistrationRepository.findByUserId(userId.toLowerCase()).orElseThrow(()->new UserNotFoundException("UserId", "userId", userId.toLowerCase()));
			return userRegistration;
			}catch (Exception e) {
				logger.error(RoomContants.ERROR_OCCURRED_DUE_TO,kv("Error Message", e.getMessage()));
				throw e;
			}
		}

}
