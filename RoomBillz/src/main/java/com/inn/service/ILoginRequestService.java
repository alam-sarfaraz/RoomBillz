package com.inn.service;

import org.springframework.http.ResponseEntity;

import com.inn.dto.AuthResponse;
import com.inn.dto.LoginRequest;

import jakarta.validation.Valid;

public interface ILoginRequestService {

	ResponseEntity<AuthResponse> login(@Valid LoginRequest loginRequest);

}
