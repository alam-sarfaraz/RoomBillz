package com.inn.customException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class GroupAlreadyExistException extends RuntimeException {
	
	public GroupAlreadyExistException(String message) {
		super(message);
	}

}
