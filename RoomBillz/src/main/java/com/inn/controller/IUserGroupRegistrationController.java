package com.inn.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.inn.dto.ErrorResponseDto;
import com.inn.dto.ResponseDto;
import com.inn.dto.UserGroupRegistrationDto;
import com.inn.entity.UserGroupDetailMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "User Group Registration Controller", description = "APIs for managing User Group Registration in RoomBillz")
@RequestMapping(path ="/userGroupRegistration", produces = {MediaType.APPLICATION_JSON_VALUE})
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
	
	
	@Operation(summary = "Find UserGroupDetailMapping by userName and groupName", description = "Retrieve UserGroupDetailMapping using their userName and groupName")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "UserGroupDetailMapping found"),
        @ApiResponse(responseCode = "404", description = "UserGroupDetailMapping not found"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", 
        content = @Content(
        		schema = @Schema(implementation = ErrorResponseDto.class)
        		)), 
    })
    @GetMapping(path = "/findUserGroupDetailByUserAndGroupName")
    ResponseEntity<UserGroupDetailMapping> findUserGroupDetailByUserAndGroupName(@Parameter(description = "Username",example = "sarfarazalam") @RequestParam(name = "userName") String userName,
    		                                                                     @Parameter(description = "GroupName",example = "RoomBillz") @RequestParam(name = "groupName") String groupName); 
	
	
	@Operation(summary = "Find UserGroupDetailMapping by userName", description = "Retrieve UserGroupDetailMapping using their userName")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "UserGroupDetailMapping found"),
        @ApiResponse(responseCode = "404", description = "UserGroupDetailMapping not found"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", 
        content = @Content(
        		schema = @Schema(implementation = ErrorResponseDto.class)
        		)), 
    })
    @GetMapping(path = "/findUserGroupDetailByUsername")
    ResponseEntity<List<UserGroupDetailMapping>> findUserGroupDetailByUsername(@Parameter(description = "Username",example = "sarfarazalam") @RequestParam(name = "userName") String userName);
	
	
	@Operation(summary = "Find UserGroupDetailMapping by groupName", description = "Retrieve UserGroupDetailMapping using their groupName")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "UserGroupDetailMapping found"),
        @ApiResponse(responseCode = "404", description = "UserGroupDetailMapping not found"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", 
        content = @Content(
        		schema = @Schema(implementation = ErrorResponseDto.class)
        		)), 
    })
    @GetMapping(path = "/findUserGroupDetailByGroupName")
    ResponseEntity<List<UserGroupDetailMapping>> findUserGroupDetailByGroupName(@Parameter(description = "GroupName",example = "RoomBillz") @RequestParam(name = "groupName") String groupName);
	
	
	@Operation(summary = "Find UserGroupDetailMapping by Email", description = "Retrieve UserGroupDetailMapping using their Email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "UserGroupDetailMapping found"),
        @ApiResponse(responseCode = "404", description = "UserGroupDetailMapping not found"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", 
        content = @Content(
        		schema = @Schema(implementation = ErrorResponseDto.class)
        		)), 
    })
    @GetMapping(path = "/findUserGroupDetailByEmail")
    ResponseEntity<List<UserGroupDetailMapping>> findUserGroupDetailByEmail(@Parameter(description = "Email",example = "sarfarazalam@example.com") @RequestParam(name = "email") String email);
	
	@Operation(summary = "Export User Group Detail Mappings by Group Name", description = "Exports User Group Detail Mappings data based on the provided Group Name.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User Group Detail Mapping exported successfully", content = @Content(mediaType = "application/octet-stream")),
			@ApiResponse(responseCode = "404", description = "No User Group Detail Mapping found for the given Group Name", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
	@GetMapping(path = "/exportUserGroupDetailMappingByGroupName", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	ResponseEntity<byte[]> exportUserGroupDetailMappingByGroupName(@Parameter(description = "Group name associated with the User Group Detail Mapping", example = "RoomBillz") 
	                                                               @RequestParam(name = "groupName") String groupName);
	
	@Operation(summary = "Export User Group Detail Mapping by Username", description = "Exports User Group Detail Mapping data based on the provided username.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User Group Detail Mapping exported successfully", content = @Content(mediaType = "application/octet-stream")),
			@ApiResponse(responseCode = "404", description = "No User Group Detail Mapping found for the given username", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
	@GetMapping(path = "/exportUserGroupDetailMappingByUsername", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	ResponseEntity<byte[]> exportUserGroupDetailMappingByUsername(@Parameter(description = "Username associated with the User Group Detail Mapping", example = "sarfarazalam") 
	                                                              @RequestParam(name = "username") String username);

	@Operation(summary = "Get User List by Group Name", description = "Returns a list of usernames associated with the given group name.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Successfully fetched user list",content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Invalid group name provided", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
			@ApiResponse(responseCode = "404", description = "Group not found", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
	@GetMapping(path = "/getUserListByGroupName", produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<List<String>> getUserListByGroupName(@Parameter(description = "Group Name", example = "RoomBillz") @RequestParam(name = "groupName") String groupName);

	@Operation(summary = "Get Emails by Group Name", description = "Returns a list of email associated with the given group name.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Successfully fetched emails list",content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "Invalid group name provided", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
			@ApiResponse(responseCode = "404", description = "Group not found", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
	@GetMapping(path = "/getEmailListByGroupName", produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<List<String>> getEmailListByGroupName(@Parameter(description = "Group Name", example = "RoomBillz") @RequestParam(name = "groupName") String groupName);

}
