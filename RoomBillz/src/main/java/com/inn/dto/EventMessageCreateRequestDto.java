package com.inn.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventMessageCreateRequestDto implements Serializable{

	private Integer id;

	private String eventType;

	private String message;

	private String sourceService;

	private String timestamp;

	private String status;

}
