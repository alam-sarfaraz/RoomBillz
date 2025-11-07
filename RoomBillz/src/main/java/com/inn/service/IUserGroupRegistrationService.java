package com.inn.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.inn.dto.ResponseDto;
import com.inn.dto.UserGroupRegistrationDto;
import com.inn.entity.UserGroupDetailMapping;

public interface IUserGroupRegistrationService {

	public ResponseEntity<ResponseDto> registerUserWithGroup(UserGroupRegistrationDto userGroupRegistrationDto);

	public ResponseEntity<UserGroupDetailMapping> findUserGroupDetailByUserAndGroupName(String userName, String groupName);

	public ResponseEntity<List<UserGroupDetailMapping>> findUserGroupDetailByUsername(String userName);

	public ResponseEntity<List<UserGroupDetailMapping>> findUserGroupDetailByGroupName(String groupName);

	public ResponseEntity<List<UserGroupDetailMapping>> findUserGroupDetailByEmail(String email);

	public ResponseEntity<byte[]> exportUserGroupDetailMappingByGroupName(String groupName);

	public ResponseEntity<byte[]> exportUserGroupDetailMappingByUsername(String username);

	public ResponseEntity<List<String>> getUserListByGroupName(String groupName);

}
