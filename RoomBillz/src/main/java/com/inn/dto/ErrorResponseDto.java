package com.inn.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ErrorResponseDto {
	
	private String apipath;
	
	private HttpStatus errorCode;
	
	private String errorMessage;
	
	private LocalDateTime errorTime;

}
