package com.inn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventMessageCreateRequestDto {

	private Integer id;

	private String eventType;

	private String message;

	private String sourceService;

	private String timestamp;

	private String status;

}
