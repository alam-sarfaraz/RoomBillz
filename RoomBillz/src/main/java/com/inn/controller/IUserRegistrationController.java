package com.inn.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.inn.dto.ErrorResponseDto;
import com.inn.dto.ResponseDto;
import com.inn.dto.UserRegistrationDto;
import com.inn.entity.UserRegistration;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "User Registration Controller", description = "APIs for managing user registration in RoomBillz")
@RequestMapping(path ="/api/v1/roomBillz", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public interface IUserRegistrationController {

    @Operation(summary = "Register a new user", description = "Create a new user with provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", 
        content = @Content(
        		schema = @Schema(implementation = ErrorResponseDto.class)
        		)), 
    })
    @PostMapping(path = "/userRegistration", consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<ResponseDto> userRegistration(@Valid @RequestBody UserRegistrationDto userRegistrationDto);

    @Operation(summary = "Find user by ID", description = "Retrieve a user's details using their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", 
        content = @Content(
        		schema = @Schema(implementation = ErrorResponseDto.class)
        		)), 
    })
    @GetMapping(path = "/findByUserId")
    ResponseEntity<UserRegistration> findByUserId(@Parameter(description = "User ID") @RequestParam(name = "id") Integer id);

    @Operation(summary = "Find user by username", description = "Retrieve user details using their username")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", 
        content = @Content(
        		schema = @Schema(implementation = ErrorResponseDto.class)
        		)), 
    })
    @GetMapping(path = "/findByUserName")
    ResponseEntity<UserRegistration> findByUserName(@Parameter(description = "Username") @RequestParam(name = "userName") String userName);

    @Operation(summary = "Delete user by ID", description = "Delete a user using their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", 
        content = @Content(
        		schema = @Schema(implementation = ErrorResponseDto.class)
        		)), 
    })
    @DeleteMapping("/deleteUserById/{id}")
    ResponseEntity<ResponseDto> deleteUserById(@Parameter(description = "User ID") @PathVariable(name = "id") Integer id);

    @Operation(summary = "Delete user by username", description = "Delete a user using their username")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", 
        content = @Content(
        		schema = @Schema(implementation = ErrorResponseDto.class)
        		)), 
    })
    @DeleteMapping("/deleteByUserName")
    ResponseEntity<ResponseDto> deleteByUserName(@Parameter(description = "Username") @RequestParam(name = "userName") String userName);

    @Operation(summary = "Update user by username", description = "Update user details using their username")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", 
        content = @Content(
        		schema = @Schema(implementation = ErrorResponseDto.class)
        		)), 
    })
    @PutMapping(path = "/updateUserByUserName", consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<ResponseDto> updateUserByUserName(
        @Parameter(description = "Username to update") @RequestParam(name = "userName") String userName,
        @RequestBody UserRegistrationDto userRegistrationDto);
}

