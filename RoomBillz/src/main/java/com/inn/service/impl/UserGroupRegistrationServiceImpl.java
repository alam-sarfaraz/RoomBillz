package com.inn.service.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inn.customException.DuplicateResourceException;
import com.inn.customException.RoomBillzException;
import com.inn.customException.UserAndGroupException;
import com.inn.customException.UserNotFoundException;
import com.inn.dto.ResponseDto;
import com.inn.dto.UserGroupRegistrationDto;
import com.inn.entity.GroupDetail;
import com.inn.entity.GroupDetailMapping;
import com.inn.entity.UserGroupDetailMapping;
import com.inn.entity.UserRegistration;
import com.inn.repository.IGroupDetailMappingRepository;
import com.inn.repository.IUserGroupDetailMappingRepository;
import com.inn.roomConstants.RoomContants;
import com.inn.service.IGroupDetailService;
import com.inn.service.IUserGroupRegistrationService;
import com.inn.service.IUserRegistrationService;

@Service
public class UserGroupRegistrationServiceImpl implements IUserGroupRegistrationService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserGroupRegistrationServiceImpl.class);
	
	@Autowired
	IUserGroupDetailMappingRepository iUserGroupDetailMappingRepository;
	
	@Autowired
	IGroupDetailMappingRepository iGroupDetailMappingRepository;
	
	@Autowired
	IUserRegistrationService iUserRegistrationService;
	
	@Autowired
	IGroupDetailService iGroupDetailService;
	
	public ResponseEntity<ResponseDto> registerUserWithGroup(UserGroupRegistrationDto userGroupRegistrationDto) {
		try {
			logger.info(RoomContants.INSIDE_THE_METHOD + "registerUserWithGroup {}", kv("UserGroupRegistrationDto",userGroupRegistrationDto));
			
			Optional<UserGroupDetailMapping> userGroupDetailMapping = iUserGroupDetailMappingRepository.findByUserNameAndGroupDetailMapping_GroupName(userGroupRegistrationDto.getUserName(),userGroupRegistrationDto.getGroupName());
			logger.info("UserGroupDetailMapping Data {}", kv("UserGroupDetailMapping",userGroupDetailMapping));
			
	        if (!userGroupDetailMapping.isEmpty()) {
	                throw new DuplicateResourceException(userGroupRegistrationDto.getUserName(),userGroupRegistrationDto.getGroupName());
	        }
	        
			UserRegistration userRegistration = iUserRegistrationService.findByUserName(userGroupRegistrationDto.getUserName());
			logger.info("UserRegistration Data {}", kv("UserRegistration",userRegistration));
			ResponseEntity<GroupDetail> groupDetail = iGroupDetailService.findByGroupName(userGroupRegistrationDto.getGroupName());
			logger.info("GroupDetailData {}", kv("GroupDetail",groupDetail));
			if(userRegistration !=null && groupDetail!=null) {
				UserGroupDetailMapping user = new UserGroupDetailMapping();
				user.setUserName(userRegistration.getUserName());
				user.setUserId(userRegistration.getUserId());
				user.setFirstName(userRegistration.getFirstName());
				user.setMiddleName(userRegistration.getMiddleName());
				user.setLastName(userRegistration.getLastName());
				user.setEmail(userRegistration.getEmail());
				user.setMobileNumber(userRegistration.getMobileNumber());
				
				GroupDetailMapping group = new GroupDetailMapping();
		        group.setGroupName(groupDetail.getBody().getGroupName());
		        group.setGroupId(groupDetail.getBody().getGroupId());
		        
		        user.setGroupDetailMapping(group);
		        group.setUserGroupDetailMapping(user);
		        
		        iUserGroupDetailMappingRepository.save(user);
			}else {
				throw new RoomBillzException("Error occurred due to Invalid Username or GroupName.");
			}
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new ResponseDto("201", "User Register in group Successfully..."));
		} catch (Exception e) {
			logger.error(RoomContants.ERROR_OCCURRED_DUE_TO,kv("Error Message", e.getMessage()));
			throw e;
		}
	}

	@Override
	public ResponseEntity<UserGroupDetailMapping> findUserGroupDetailByUserAndGroupName(String userName,
			String groupName) {
		try {
			logger.info(RoomContants.INSIDE_THE_METHOD + "findUserGroupDetailByUserAndGroupName {}",
					kv("userName", userName), kv("groupName", groupName));
			UserGroupDetailMapping detailMapping = iUserGroupDetailMappingRepository
					.findByUserNameAndGroupDetailMapping_GroupName(userName, groupName)
					.orElseThrow(() -> new UserAndGroupException(userName, groupName));
			return ResponseEntity.status(HttpStatus.OK).body(detailMapping);
		} catch (Exception e) {
			logger.error(RoomContants.ERROR_OCCURRED_DUE_TO, kv("Error Message", e.getMessage()));
			throw e;
		}
	}

	@Override
	public ResponseEntity<List<UserGroupDetailMapping>> findUserGroupDetailByUsername(String userName) {
		try {
			logger.info(RoomContants.INSIDE_THE_METHOD + "findUserGroupDetailByUsername {}", kv("userName", userName));
			List<UserGroupDetailMapping> userGroupDetailMappingList = iUserGroupDetailMappingRepository
					.findByUserName(userName);
			if (userGroupDetailMappingList != null && userGroupDetailMappingList.isEmpty()) {
				throw new UserNotFoundException("Username", "Username", userName);
			}
			return ResponseEntity.status(HttpStatus.OK).body(userGroupDetailMappingList);
		} catch (Exception e) {
			logger.error(RoomContants.ERROR_OCCURRED_DUE_TO, kv("Error Message", e.getMessage()));
			throw e;
		}
	}

	@Override
	public ResponseEntity<List<UserGroupDetailMapping>> findUserGroupDetailByGroupName(String groupName) {
		try {
			logger.info(RoomContants.INSIDE_THE_METHOD + "findUserGroupDetailByGroupName {}",
					kv("GroupName", groupName));
			List<UserGroupDetailMapping> userGroupDetailMappingList = iUserGroupDetailMappingRepository
					.findByGroupDetailMapping_GroupName(groupName);
			if (userGroupDetailMappingList != null && userGroupDetailMappingList.isEmpty()) {
				throw new UserNotFoundException("GroupName", "GroupName", groupName);
			}
			return ResponseEntity.status(HttpStatus.OK).body(userGroupDetailMappingList);
		} catch (Exception e) {
			logger.error(RoomContants.ERROR_OCCURRED_DUE_TO, kv("Error Message", e.getMessage()));
			throw e;
		}
	}

	@Override
	public ResponseEntity<List<UserGroupDetailMapping>> findUserGroupDetailByEmail(String email) {
		try {
			logger.info(RoomContants.INSIDE_THE_METHOD + "findUserGroupDetailByEmail {}", kv("Email", email));
			List<UserGroupDetailMapping> userGroupDetailMappingList = iUserGroupDetailMappingRepository.findByEmail(email);
			if (userGroupDetailMappingList != null && userGroupDetailMappingList.isEmpty()) {
				throw new UserNotFoundException("Email", "Email", email);
			}
			return ResponseEntity.status(HttpStatus.OK).body(userGroupDetailMappingList);
		} catch (Exception e) {
			logger.error(RoomContants.ERROR_OCCURRED_DUE_TO, kv("Error Message", e.getMessage()));
			throw e;
		}
	}

}
