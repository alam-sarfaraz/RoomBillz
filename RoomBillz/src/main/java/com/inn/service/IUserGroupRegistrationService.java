package com.inn.service;

import org.springframework.http.ResponseEntity;

import com.inn.dto.ResponseDto;
import com.inn.dto.UserGroupRegistrationDto;

public interface IUserGroupRegistrationService {

	ResponseEntity<ResponseDto> registerUserWithGroup(UserGroupRegistrationDto userGroupRegistrationDto);

}
