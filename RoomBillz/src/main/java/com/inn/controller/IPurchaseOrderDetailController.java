package com.inn.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.inn.dto.ErrorResponseDto;
import com.inn.dto.PurchaseOrderDetailDto;
import com.inn.dto.ResponseDto;
import com.inn.entity.PurchaseOrderDetail;

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
	    @ApiResponse(responseCode = "500",description = "HTTP Status Internal Server Error",
	    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
	@PostMapping(path = "/createPurchaseOrder", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDto> createPurchaseOrder(@Parameter(description = "Purchase order details",required = true,
	                                                       content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
	                                                       schema =  @Schema(implementation = PurchaseOrderDetailDto.class)))
	                                                       @Valid @RequestPart("purchaseOrder") PurchaseOrderDetailDto purchaseOrderDetailDto,
                                                           @Parameter(description = "List of invoice files", required = false)
	                                                       @RequestPart("files") List<MultipartFile> invoiceFiles);
	
	@Operation(summary = "Find all Purchase Order Details", description = "Retrieve all Purchase Order Details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Purchase Order Detail found"),
        @ApiResponse(responseCode = "404", description = "Purchase Order Detail not found"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", 
        content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),})
	@GetMapping(path = "/findAllPurchaseOrderDetail")
    ResponseEntity<List<PurchaseOrderDetail>> findAllPurchaseOrderDetail();
	
	
	@Operation(summary = "Find Purchase Order Details by Id", description = "Retrieve a Purchase Order Details using their Id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Purchase Order Detail found"),
        @ApiResponse(responseCode = "404", description = "Purchase Order Detail not found"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", 
        content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),})
	@GetMapping(path = "/findPurchaseOrderDetailById")
    ResponseEntity<PurchaseOrderDetail> findPurchaseOrderDetailById(@Parameter(description = "Id",example = "1") @RequestParam(name = "id") Integer id);
	
	
	@Operation(summary = "Find Purchase Order Details by UserName", description = "Retrieve a Purchase Order Details using their UserName")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Purchase Order Detail found"),
        @ApiResponse(responseCode = "404", description = "Purchase Order Detail not found"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", 
        content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),})
	@GetMapping(path = "/findPurchaseOrderDetailByUserName")
    ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrderDetailByUserName(@Parameter(description = "UserName",example = "sarfarazalam") @RequestParam(name = "userName") String userName);
	
	
	@Operation(summary = "Find Purchase Order Details by GroupName", description = "Retrieve a Purchase Order Details using their GroupName")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Purchase Order Detail found"),
        @ApiResponse(responseCode = "404", description = "Purchase Order Detail not found"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", 
        content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),})
	@GetMapping(path = "/findPurchaseOrderDetailByGroupName")
    ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrderDetailByGroupName(@Parameter(description = "GroupName",example = "RoomBillz") @RequestParam(name = "groupName") String groupName);
	
	
	@Operation(summary = "Find Purchase Order Details by purchaseId", description = "Retrieve a Purchase Order Details using their purchaseId")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Purchase Order Detail found"),
        @ApiResponse(responseCode = "404", description = "Purchase Order Detail not found"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", 
        content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),})
	@GetMapping(path = "/findPurchaseOrderDetailByPurchaseId")
    ResponseEntity<PurchaseOrderDetail> findPurchaseOrderDetailByPurchaseId(@Parameter(description = "PurchaseId",example = "PO-00002") @RequestParam(name = "purchaseId") String purchaseId);

	@Operation(summary = "Find Purchase Order Details by Month", description = "Retrieve a Purchase Order Details using their Month")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Purchase Order Detail found"),
        @ApiResponse(responseCode = "404", description = "Purchase Order Detail not found"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", 
        content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),})
	@GetMapping(path = "/findPurchaseOrderDetailByMonth")
    ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrderDetailByMonth(@Parameter(description = "Month",example = "August") @RequestParam(name = "month") String month);
	
	@Operation(summary = "Find Purchase Order Details by Date", description = "Retrieve a Purchase Order Details using their Date")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Purchase Order Detail found"),
        @ApiResponse(responseCode = "404", description = "Purchase Order Detail not found"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", 
        content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),})
	@GetMapping(path = "/findPurchaseOrderDetailByDate")
    ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrderDetailByDate(@Parameter(description = "Date",example = "2025-08-06") @RequestParam(name = "date") LocalDate date);
	
	
	@Operation(summary = "Find Purchase Order Details by UserName and GroupName", description = "Retrieve a Purchase Order Details using their UserName and GroupName")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Purchase Order Detail found"),
        @ApiResponse(responseCode = "404", description = "Purchase Order Detail not found"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", 
        content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),})
	@GetMapping(path = "/findPurchaseOrdByUserAndGroupName")
    ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrdByUserAndGroupName(@Parameter(description = "UserName",example = "sarfarazalam") @RequestParam(name = "userName") String userName,
    		                                                                    @Parameter(description = "GroupName",example = "RoomBillz") @RequestParam(name = "groupName") String groupName);
	
	@Operation(summary = "Find Purchase Order Details by UserName and PurchaseId", description = "Retrieve a Purchase Order Details using their UserName and PurchaseId")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Purchase Order Detail found"),
        @ApiResponse(responseCode = "404", description = "Purchase Order Detail not found"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", 
        content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),})
	@GetMapping(path = "/findPurchaseOrdByUserAndPurchaseId")
    ResponseEntity<PurchaseOrderDetail> findPurchaseOrdByUserAndPurchaseId(@Parameter(description = "UserName",example = "sarfarazalam") @RequestParam(name = "userName") String userName,
    		                                                               @Parameter(description = "PurchaseId",example = "PO-00002") @RequestParam(name = "purchaseId") String purchaseId);
	
	@Operation(summary = "Find Purchase Order Details by UserName and Date", description = "Retrieve a Purchase Order Details using their UserName and Date")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Purchase Order Detail found"),
        @ApiResponse(responseCode = "404", description = "Purchase Order Detail not found"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", 
        content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),})
	@GetMapping(path = "/findPurchaseOrdByUserNameAndDate")
    ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrdByUserNameAndDate(@Parameter(description = "UserName",example = "sarfarazalam") @RequestParam(name = "userName") String userName,
    		                                                             @Parameter(description = "Date",example = "2025-08-06") @RequestParam(name = "date") LocalDate date);
	
	@Operation(summary = "Find Purchase Order Details by UserName and GroupName and Date", description = "Retrieve a Purchase Order Details using their UserName and GroupName and Date")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Purchase Order Detail found"),
        @ApiResponse(responseCode = "404", description = "Purchase Order Detail not found"),
        @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", 
        content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),})
	@GetMapping(path = "/findPurchaseOrdByUserGroupAndDate")
    ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrdByUserAndGroupAndDate(@Parameter(description = "UserName",example = "sarfarazalam") @RequestParam(name = "userName") String userName,
    		                                                                       @Parameter(description = "GroupName",example = "RoomBillz") @RequestParam(name = "groupName") String groupName,
    		                                                                       @Parameter(description = "Date",example = "2025-08-06") @RequestParam(name = "date") LocalDate date);


	@Operation(summary = "Download PurchaseOrder Details Invoice Details by purchaseId",description = "Retrieve and download Purchase Order Details Invoice Details by purchaseId")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "File downloaded successfully"),
		@ApiResponse(responseCode = "404", description = "Purchase Order Detail not found"),
		@ApiResponse(responseCode = "500", description = "Internal Server Error",
		content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),})
	@GetMapping(path = "/downloadPurchaseOrderDetailByPurchaseId", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> downloadPurchaseOrderDetailByPurchaseId(
		        @Parameter(description = "PurchaseId", example = "PO-00002")
		        @RequestParam(name = "purchaseId") String purchaseId);
		
		
	@Operation(summary = "Delete PurchaseOrder Details by purchaseId",description = "Delete PurchaseOrder Details by purchaseId")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Delete PurchaseOrder Details successfully"),
	    @ApiResponse(responseCode = "404", description = "Purchase Order Detail not found"),
	    @ApiResponse(responseCode = "500", description = "Internal Server Error",
	    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),})
	@DeleteMapping(path = "/deletePurchaseOrderDetailByPurchaseId", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> deletePurchaseOrderDetailByPurchaseId(@Parameter(description = "PurchaseId", example = "PO-00000")
			                                                                 @RequestParam(name = "purchaseId") String purchaseId);
		
		
	@Operation(summary = "Export Purchase Order Details by Username, Group Name, and Month",description = "Exports Purchase Order Details based on the provided Username, Group Name, and Month.")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Purchase Order Details exported successfully",content = @Content(mediaType = "application/octet-stream")),
	    @ApiResponse(responseCode = "404", description = "Purchase Order Details not found",content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
	    @ApiResponse(responseCode = "500", description = "Internal Server Error",content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
	@GetMapping(path = "/exportPODetailByUsernameGroupAndMonthWise", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	ResponseEntity<byte[]> exportPODetailByUsernameGroupAndMonthWise(@Parameter(description = "Username of the requester or owner of the Purchase Orders", example = "sarfarazalam")
			                                                         @RequestParam(name = "username", required = true) String username,
                                                                     @Parameter(description = "Group name associated with the Purchase Orders", example = "RoomBillz")
			                                                         @RequestParam(name = "groupName", required = true) String groupName,
                                                                     @Parameter(description = "Month for which Purchase Order Details are to be exported (format: August)", example = "August")
			                                                         @RequestParam(name = "month", required = true) String month);
			
	@Operation(summary = "Export Purchase Order Details by Username, Group Name, Status, and Month",description = "Exports Purchase Order details based on the provided Username, Group Name, Status, and Month.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200",description = "Purchase Order details exported successfully",content = @Content(mediaType = "application/octet-stream")),
        @ApiResponse(responseCode = "404",description = "No Purchase Order details found for the given criteria",content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
	    @ApiResponse(responseCode = "500",description = "Internal Server Error",content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
	@GetMapping(path = "/exportPODetailByUsernameGroupStatusAndMonthWise",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	ResponseEntity<byte[]> exportPODetailByUsernameGroupStatusAndMonthWise(@Parameter(description = "Username of the requester or owner of the Purchase Orders",example = "sarfarazalam")
				                                                  @RequestParam(name = "username") String username,
                                                                  @Parameter(description = "Group name associated with the Purchase Orders",example = "RoomBillz")
				                                                  @RequestParam(name = "groupName") String groupName,
                                                                  @Parameter(description = "Status of the Purchase Orders (e.g., PENDING, APPROVED, REJECTED, PARTIALLY_APPROVED)",example = "Pending")
				                                                  @RequestParam(name = "status") String status,
				                                                  @Parameter(description = "Month for which Purchase Order details are to be exported (format: August)",example = "August")
				                                                  @RequestParam(name = "month") String month);
	
	@Operation(summary = "Export Purchase Order Details by Month",description = "Exports Purchase Order details based on the provided month.")
		@ApiResponses(value = {
		    @ApiResponse(responseCode = "200",description = "Purchase Order details exported successfully",content = @Content(mediaType = "application/octet-stream")),
		    @ApiResponse(responseCode = "404",description = "No Purchase Order details found for the given month",content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
		    @ApiResponse(responseCode = "500",description = "Internal Server Error",content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
	@GetMapping(path = "/exportPODetailsByMonth",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	ResponseEntity<byte[]> exportPODetailsByMonth(@Parameter(description = "Month for which Purchase Order details are to be exported (e.g., August)",example = "August",required = true)
		                                          @RequestParam(name = "month") String month);
	
	@Operation(summary = "Export Purchase Order Details by Status",description = "Exports Purchase Order details based on the provided Status.")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200",description = "Purchase Order details exported successfully",content = @Content(mediaType = "application/octet-stream")),
	    @ApiResponse(responseCode = "404",description = "No Purchase Order details found for the given Status",content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
	    @ApiResponse(responseCode = "500",description = "Internal Server Error",content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    @GetMapping(path = "/exportPODetailStatus",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    ResponseEntity<byte[]> exportPODetailStatus(@Parameter(description = "Status for which Purchase Order details are to be exported (e.g., Status)",example = "Pending",required = true)
	                                            @RequestParam(name = "status") String status);
	



}