package com.inn.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventMessage implements Serializable {

	private String eventType;
	private String message;
	private String sourceService;
	private String timestamp;

}