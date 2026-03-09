package com.inn.service.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inn.customException.InvalidCredentialsException;
import com.inn.dto.AuthResponse;
import com.inn.dto.LoginRequest;
import com.inn.dto.RoleDTO;
import com.inn.entity.UserRegistration;
import com.inn.repository.IUserRegistrationRepository;
import com.inn.roomConstants.RoomConstants;
import com.inn.service.ILoginRequestService;

import jakarta.validation.Valid;

@Service
public class LoginRequestServiceImpl implements ILoginRequestService {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginRequestServiceImpl.class);
	
	@Autowired
	IUserRegistrationRepository iUserRegistrationRepository;

	@Override
	public ResponseEntity<AuthResponse> login(@Valid LoginRequest loginRequest) {
	    try {
	        logger.info(RoomConstants.INSIDE_THE_METHOD + "login :",kv("LoginRequest", loginRequest));

	        Optional<UserRegistration> userRegistration =iUserRegistrationRepository
	        		                                     .findByUserNameAndPassword(loginRequest.getUsername(),loginRequest.getPassword());

	        if (userRegistration.isPresent()) {
	            UserRegistration user = userRegistration.get();
	            AuthResponse response = new AuthResponse();
	            response.setId(user.getId());
	            response.setUserId(user.getUserId());
	            response.setUserName(user.getUserName());
	            response.setFirstName(user.getFirstName());
	            response.setMiddleName(user.getMiddleName());
	            response.setLastName(user.getLastName());
	            response.setEmail(user.getEmail());
	            response.setPassword(user.getPassword());
	            response.setIsActive(user.getIsActive());

	            List<RoleDTO> roles = user.getRoles()
	                    .stream()
	                    .map(role -> {
	                        RoleDTO dto = new RoleDTO();
	                        dto.setId(role.getId());
	                        dto.setRoleName(role.getRoleName());
	                        return dto;
	                    })
	                    .toList();

	            response.setRoles(roles);
	            return ResponseEntity.ok(response);
	        } else {
	        	 throw new InvalidCredentialsException("Invalid username or password");
	        }

	    } catch (Exception e) {
	        logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO,kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
	        throw e;
	    }
	}

}
