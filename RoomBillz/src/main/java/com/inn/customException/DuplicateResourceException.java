package com.inn.customException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DuplicateResourceException extends RuntimeException {
    
	public DuplicateResourceException(String userName,String groupName) {
		super(String.format("Username - '%s' and Groupname - '%s' Already Register.",userName,groupName));
    }
}