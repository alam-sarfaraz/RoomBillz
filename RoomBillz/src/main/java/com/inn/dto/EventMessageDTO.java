package com.inn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventMessageDTO {
    
	private String eventType;
    private String message;
    private String sourceService;
    private String timestamp;
    
}
