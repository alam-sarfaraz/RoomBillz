package com.inn.customException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RoomBillzException extends RuntimeException{
	
	public RoomBillzException(String message) {
		super(message);
	}
	

}
