package com.inn.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.inn.dto.ErrorResponseDto;
import com.inn.dto.GroupDetailDto;
import com.inn.dto.ResponseDto;
import com.inn.entity.GroupDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Group Detail Controller", description = "APIs for managing user Group Detail in RoomBillz")
@RequestMapping(path ="/api/v1/roomBillz", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public interface IGroupDetailController {
	
	@Operation(summary = "Create a new Group Detail", description = "Create a new Group Detail with provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Group Detail created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", 
        content = @Content(
        		schema = @Schema(implementation = ErrorResponseDto.class)
        		)), 
    })
    @PostMapping(path = "/createGroupDetail", consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<ResponseDto> createGroupDetail(@Valid @RequestBody GroupDetailDto groupDetailDto);
	
	
	@Operation(summary = "Find Group Detail by Id", description = "Retrieve a Group Detail using their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Group Detail found"),
        @ApiResponse(responseCode = "404", description = "Group Detail not found"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", 
        content = @Content(
        		schema = @Schema(implementation = ErrorResponseDto.class)
        		)), 
    })
	@GetMapping(path = "/findGroupDetailById")
    ResponseEntity<GroupDetail> findGroupDetailById(@Parameter(description = "ID") @RequestParam(name = "id") Integer id);
	
	
	@Operation(summary = "Find Group Detail by Group Name", description = "Retrieve a Group Detail using their Group Name")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Group Detail found"),
        @ApiResponse(responseCode = "404", description = "Group Detail not found"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", 
        content = @Content(
        		schema = @Schema(implementation = ErrorResponseDto.class)
        		)), 
    })
	@GetMapping(path = "/findByGroupName")
    ResponseEntity<GroupDetail> findByGroupName(@Parameter(description = "Group Name") @RequestParam(name = "groupName") String groupName);
	
    @Operation(summary = "Delete Group Detail by ID", description = "Delete a  Group Detail using their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Group Detail deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Group Detail not found"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", 
        content = @Content(
        		schema = @Schema(implementation = ErrorResponseDto.class)
        		)), 
    })
    @DeleteMapping("/deleteGroupById/{id}")
    ResponseEntity<ResponseDto> deleteGroupById(@Parameter(description = "ID") @PathVariable(name = "id") Integer id);
    
    @Operation(summary = "Delete Group Detail by groupname", description = "Delete a Group Detail using their groupname")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Group Detail deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Group Detail not found"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", 
        content = @Content(
        		schema = @Schema(implementation = ErrorResponseDto.class)
        		)), 
    })
    @DeleteMapping("/deleteByGroupName")
    ResponseEntity<ResponseDto> deleteByGroupName(@Parameter(description = "Groupname") @RequestParam(name = "groupName") String groupName);
}
