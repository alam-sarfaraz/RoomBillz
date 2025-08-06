package com.inn.service;

import org.springframework.http.ResponseEntity;

import com.inn.dto.ResponseDto;
import com.inn.dto.UserGroupRegistrationDto;
import com.inn.entity.UserGroupDetailMapping;

public interface IUserGroupRegistrationService {

	ResponseEntity<ResponseDto> registerUserWithGroup(UserGroupRegistrationDto userGroupRegistrationDto);

	ResponseEntity<UserGroupDetailMapping> findUserGroupDetailByUserAndGroupName(String userName, String groupName);

}
