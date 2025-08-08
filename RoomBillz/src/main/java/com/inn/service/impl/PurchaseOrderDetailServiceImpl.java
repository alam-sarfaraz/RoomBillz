package com.inn.service.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.io.File;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.inn.customException.RoomBillzException;
import com.inn.dto.PurchaseOrderDetailDto;
import com.inn.dto.ResponseDto;
import com.inn.entity.GroupDetail;
import com.inn.entity.InvoiceDetail;
import com.inn.entity.LineItemDetail;
import com.inn.entity.PurchaseOrderDetail;
import com.inn.entity.UserRegistration;
import com.inn.repository.IInvoiceDetailRepository;
import com.inn.repository.ILineItemDetailRepository;
import com.inn.repository.IPurchaseOrderDetailRepository;
import com.inn.repository.IUserGroupDetailMappingRepository;
import com.inn.roomConstants.RoomContants;
import com.inn.service.IGroupDetailService;
import com.inn.service.IPurchaseOrderDetailService;
import com.inn.service.IUserRegistrationService;

import jakarta.validation.Valid;

@Service
public class PurchaseOrderDetailServiceImpl implements IPurchaseOrderDetailService{
	
	private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderDetailServiceImpl.class);

	@Autowired
	IPurchaseOrderDetailRepository iPurchaseOrderDetailRepository;
	
	@Autowired
	ILineItemDetailRepository iLineItemDetailRepository;
	
	@Autowired
	IInvoiceDetailRepository iInvoiceDetailRepository;
	
	@Autowired
	IUserGroupDetailMappingRepository iUserGroupDetailMappingRepository;
	
	@Autowired
	IUserRegistrationService iUserRegistrationService;
	
	@Autowired
	IGroupDetailService iGroupDetailService;

	@Override
	public ResponseEntity<ResponseDto> createPurchaseOrder(@Valid PurchaseOrderDetailDto purchaseOrderDetailDto,List<MultipartFile> invoiceFiles) {
		try {
			logger.info(RoomContants.INSIDE_THE_METHOD + "createPurchaseOrder {}",kv("PurchaseOrderDetailDto", purchaseOrderDetailDto));
			
			// Validation for user name and group is the part of that group or not.
			iUserGroupDetailMappingRepository.findByUserNameAndGroupDetailMapping_GroupName(purchaseOrderDetailDto.getUserName(),purchaseOrderDetailDto.getGroupName())
	                                      .orElseThrow(() -> new RoomBillzException(String.format("You are not part of the group: '%s'", purchaseOrderDetailDto.getGroupName())));
			
			// Fetching userDetail from DB.
			UserRegistration userRegistration = iUserRegistrationService.findByUserName(purchaseOrderDetailDto.getUserName());
			
			// Fetching groupDetail from DB.
			GroupDetail groupDetail = iGroupDetailService.findByGroupName(purchaseOrderDetailDto.getGroupName()).getBody();
			
			PurchaseOrderDetail purchaseOrderDetail = new PurchaseOrderDetail();
			purchaseOrderDetail.setPurchaseId("");
			purchaseOrderDetail.setPurchaseDate(purchaseOrderDetailDto.getPurchaseDate());
			purchaseOrderDetail.setUserName(purchaseOrderDetailDto.getUserName());
			purchaseOrderDetail.setUserId(userRegistration.getUserId());
			purchaseOrderDetail.setFirstName(userRegistration.getFirstName());
			purchaseOrderDetail.setMiddleName(userRegistration.getMiddleName());
			purchaseOrderDetail.setLastName(userRegistration.getLastName());
			purchaseOrderDetail.setEmail(userRegistration.getEmail());
			purchaseOrderDetail.setMobileNumber(userRegistration.getMobileNumber());
			purchaseOrderDetail.setGroupId(groupDetail.getGroupId());
			purchaseOrderDetail.setGroupName(groupDetail.getGroupName());
			purchaseOrderDetail.setStatus("Pending");
			purchaseOrderDetail.setModeOfPayment(purchaseOrderDetail.getModeOfPayment());
			purchaseOrderDetail.setMonth(purchaseOrderDetailDto.getPurchaseDate().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
			
			List<LineItemDetail> lineItemDetailList = new ArrayList<>();
			LineItemDetail lineItemDetail = new LineItemDetail();
			purchaseOrderDetailDto.getItemDetails().forEach(item ->{
				lineItemDetail.setItemName(item.getItemName());
				lineItemDetail.setItemPrice(item.getItemPrice());
				lineItemDetail.setUnitPrice(item.getUnitPrice());
				lineItemDetail.setQuantity(item.getQuantity());
				lineItemDetail.setPurchaseOrder(purchaseOrderDetail);
				lineItemDetailList.add(lineItemDetail);
			});
			
			List<InvoiceDetail> invoiceFileDetailList = setInvoiceFileDetails(invoiceFiles,purchaseOrderDetail);
			logger.info("Invoice File Detail List {}",kv("invoiceFileDetailList", invoiceFileDetailList));
			purchaseOrderDetail.setLineItemDetails(lineItemDetailList);
			purchaseOrderDetail.setInvoiceDetails(invoiceFileDetailList);
			iPurchaseOrderDetailRepository.save(purchaseOrderDetail);
			
	       return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto("201","Purchase Order saved Successfully."));
		} catch (Exception e) {
			logger.error(RoomContants.ERROR_OCCURRED_DUE_TO, kv("Error Message", e.getMessage()));
			throw e;
		}
	}
		
	public List<InvoiceDetail> setInvoiceFileDetails(List<MultipartFile> files,PurchaseOrderDetail purchaseOrderDetail) {
		 List<InvoiceDetail> invoiceDetailList = new ArrayList<>();
		 InvoiceDetail invoiceDetail = new InvoiceDetail();
		 String uploadPath="To be decided"; 
	    if (files == null || files.isEmpty()) {
	        throw new RoomBillzException("File list is missing or empty");
	    }
	    for (int i = 0; i < files.size(); i++) {
	        MultipartFile file = files.get(i);

	        if (file == null || file.isEmpty()) {
	            throw new RoomBillzException("File at index " + i + " is missing or empty");
	        }

	        String originalFileName = file.getOriginalFilename();
	        String extension = "";

	        if (originalFileName != null && originalFileName.contains(".")) {
	            extension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1);
	        }
	        String fullPath = uploadPath + File.separator + originalFileName;
	        invoiceDetail.setFileName(originalFileName);
	        invoiceDetail.setFilePath(fullPath);
	        invoiceDetail.setFileSize(file.getSize());
	        invoiceDetail.setExtension(extension);
	        invoiceDetail.setPurchaseOrder(purchaseOrderDetail);
	        invoiceDetailList.add(invoiceDetail);
	    }
	    return invoiceDetailList;
	}
	
	
	
}
