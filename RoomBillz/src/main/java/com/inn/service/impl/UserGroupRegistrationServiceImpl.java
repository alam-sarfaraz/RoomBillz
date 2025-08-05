package com.inn.service.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inn.customException.DuplicateResourceException;
import com.inn.customException.RoomBillzException;
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
			
			boolean userExists = iUserGroupDetailMappingRepository.existsByUserName(userGroupRegistrationDto.getUserName());
	        boolean groupExists = iGroupDetailMappingRepository.existsByGroupName(userGroupRegistrationDto.getGroupName());
	        
	        if (userExists && groupExists) {
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

}
