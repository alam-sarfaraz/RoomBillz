package com.inn.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inn.dto.AuthResponse;
import com.inn.dto.ErrorResponseDto;
import com.inn.dto.LoginRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Login Controller", description = "APIs for managing login requests in RoomBillz")
@RequestMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public interface ILoginRequestController {

	@Operation(summary = "User Login", description = "Authenticate user using username/email and password.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Login successful"),
			@ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })

	@PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest);

}
