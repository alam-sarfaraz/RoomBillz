package com.inn.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inn.dto.ErrorResponseDto;
import com.inn.dto.ResponseDto;
import com.inn.dto.UserGroupRegistrationDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "User Group Registration Controller", description = "APIs for managing User Group Registration in RoomBillz")
@RequestMapping(path ="/api/v1/roomBillz", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public interface IUserGroupRegistrationController {
	
	@Operation(summary = "Register a User With Group", description = "Register a User With Group with provided Detail")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User registered With Group successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", 
        content = @Content(
        		schema = @Schema(implementation = ErrorResponseDto.class)
        		)), 
    })
    @PostMapping(path = "/registerUserWithGroup", consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<ResponseDto> registerUserWithGroup(@Valid @RequestBody UserGroupRegistrationDto userGroupRegistrationDto);
	
	

}
