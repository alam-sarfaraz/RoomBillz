package com.inn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class ResponseDto {
	
	private String statusCode;
	
	private String statusMessage;

}
