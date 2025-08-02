package com.inn.service;

import org.springframework.http.ResponseEntity;

import com.inn.dto.ResponseDto;
import com.inn.dto.UserRegistrationDto;
import com.inn.entity.UserRegistration;

public interface IUserRegistrationService {

	public ResponseEntity<ResponseDto> userRegistration(UserRegistrationDto userRegistrationDto);
	
	public  UserRegistration findById(Integer id);
	
	public UserRegistration findByUserName(String userName);
	
	public ResponseEntity<ResponseDto> deleteUserById(Integer id);

	public ResponseEntity<ResponseDto> deleteByUserName(String userName);

	public ResponseEntity<ResponseDto> updateUserByUserName(String userName, UserRegistrationDto userRegistrationDto);


}
