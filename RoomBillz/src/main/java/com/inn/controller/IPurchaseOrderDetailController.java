package com.inn.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.inn.dto.ErrorResponseDto;
import com.inn.dto.PurchaseOrderDetailDto;
import com.inn.dto.ResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Purchase Order Detail Controller", description = "APIs for managing user Purchase Order Detail in RoomBillz")
@RequestMapping(path ="/api/v1/roomBillz")
@Validated
public interface IPurchaseOrderDetailController {
	
	@Operation(summary = "Create a new Purchase Order Detail", description = "Create a new Purchase Order Detail")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "201", description = "Purchase Order Detail created successfully"),
	    @ApiResponse(responseCode = "400", description = "Invalid input"),
	    @ApiResponse(responseCode = "500",
	                 description = "HTTP Status Internal Server Error",
	                 content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
	                )
	                })
	@PostMapping(path = "/createPurchaseOrder", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDto> createPurchaseOrder(@Parameter(description = "Purchase order details",required = true,
	                                                       content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
	                                                       schema =  @Schema(implementation = PurchaseOrderDetailDto.class)))
	                                                       @Valid @RequestPart("purchaseOrder") PurchaseOrderDetailDto purchaseOrderDetailDto,
                                                           @Parameter(description = "List of invoice files", required = true)
	                                                       @RequestPart("files") List<MultipartFile> invoiceFiles);

 

}
