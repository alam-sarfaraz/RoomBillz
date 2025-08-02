package com.inn.customException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.inn.dto.ErrorResponseDto;

@ControllerAdvice

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
	        MethodArgumentNotValidException ex,
	        HttpHeaders headers,
	        HttpStatusCode status,
	        WebRequest request) {

	    Map<String, String> validationErrors = new HashMap<>();
	    List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

	    fieldErrors.forEach(error -> {
	        String fieldName = error.getField();
	        String errorMessage = error.getDefaultMessage();
	        validationErrors.put(fieldName, errorMessage);
	    });

	    return new ResponseEntity<>(validationErrors, headers, status);
	}


	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception,WebRequest webRequest){
		ErrorResponseDto errorResponseDto = new ErrorResponseDto();
		errorResponseDto.setApipath(webRequest.getDescription(false));
		errorResponseDto.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR);
		errorResponseDto.setErrorMessage(exception.getMessage());
		errorResponseDto.setErrorTime(LocalDateTime.now());
		return new ResponseEntity<>(errorResponseDto,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UserAlreadyExistException.class)
	public ResponseEntity<ErrorResponseDto> handleUserAlreadyExistException(UserAlreadyExistException alreadyExistException,WebRequest webRequest){
		ErrorResponseDto errorResponseDto = new ErrorResponseDto();
		errorResponseDto.setApipath(webRequest.getDescription(false));
		errorResponseDto.setErrorCode(HttpStatus.BAD_REQUEST);
		errorResponseDto.setErrorMessage(alreadyExistException.getMessage());
		errorResponseDto.setErrorTime(LocalDateTime.now());
		return new ResponseEntity<>(errorResponseDto,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(UserNotFoundException userNotFoundException,WebRequest webRequest){
		ErrorResponseDto errorResponseDto = new ErrorResponseDto();
		errorResponseDto.setApipath(webRequest.getDescription(false));
		errorResponseDto.setErrorCode(HttpStatus.BAD_REQUEST);
		errorResponseDto.setErrorMessage(userNotFoundException.getMessage());
		errorResponseDto.setErrorTime(LocalDateTime.now());
		return new ResponseEntity<>(errorResponseDto,HttpStatus.NOT_FOUND);
	}

	
}
