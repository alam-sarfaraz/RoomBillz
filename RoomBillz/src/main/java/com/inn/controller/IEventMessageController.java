package com.inn.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.inn.dto.ErrorResponseDto;
import com.inn.dto.ResponseDto;
import com.inn.entity.EventMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Event Message Controller", description = "APIs for managing Event Message in RoomBillz")
@RequestMapping(path ="/eventMessage", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public interface IEventMessageController {
	
	@Operation(summary = "Get the earliest failed event message",description = "Returns the failed event message that was created earliest (first in chronological order).")
		@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Failed event message found"),
		    @ApiResponse(responseCode = "404", description = "No failed event message found",content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
		    @ApiResponse(responseCode = "500", description = "Internal Server Error",content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
		})
		@GetMapping(path = "/findEarliestFailedEvent")
		EventMessage findEarliestFailedEvent();
	
		@Operation(summary = "Update event message by status", description = "Updates the status of the earliest failed event message to the given status")
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Event message updated successfully"),
				@ApiResponse(responseCode = "404", description = "No failed event message found", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
				@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
		@PutMapping(path = "/updateEventMessageByStatus")
		ResponseEntity<ResponseDto> updateEventMessageByStatus(@Parameter(description = "ID",example = "1") @RequestParam(name = "id") Integer id,
				                                               @Parameter(description = "New status to update", example = "Success") @RequestParam(name = "status") String status);
		
		@Operation(summary = "Send failed purchase order detail to Notification Service (via scheduler)", description = "Finds the earliest failed purchase order event and attempts to resend it to the Notification Service.")
		@ApiResponses(value = {
				@ApiResponse(responseCode = "200", description = "Event message sent or retried successfully"),
				@ApiResponse(responseCode = "404", description = "No failed event message found", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
				@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))) })
		@GetMapping("/sendFailedPurchaseOrderToNotification")
		ResponseEntity<ResponseDto> sendFailedPurchaseOrderDetailToNotificationService();




}
