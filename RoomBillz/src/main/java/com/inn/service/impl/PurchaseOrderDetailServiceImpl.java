package com.inn.service.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${invoice.upload.path}")
	private String basePath;

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
			
			// Creating PurchaseOrderDetail.
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
			
			// Creating LineItemDetail.
			List<LineItemDetail> lineItemDetailList = new ArrayList<>();
			purchaseOrderDetailDto.getItemDetails().forEach(item ->{
				LineItemDetail lineItemDetail = new LineItemDetail();
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
		
	public List<InvoiceDetail> setInvoiceFileDetails(List<MultipartFile> files, PurchaseOrderDetail purchaseOrderDetail) {
	    try {
	        logger.info(RoomContants.INSIDE_THE_METHOD + "SetInvoiceFileDetails");

	        if (files == null || files.isEmpty()) {
	            throw new RoomBillzException("File list is missing or empty");
	        }

	        List<InvoiceDetail> invoiceDetailList = new ArrayList<>();
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(RoomContants.DATE_FORMAT);
	        String datePath = simpleDateFormat.format(new Date());

	        Path path = Paths.get(basePath, datePath);
	        if (Files.notExists(path)) {
	            Files.createDirectories(path);
	            logger.info("Created directory: {}", path);
	        }
	        logger.info("BasePath: {}", basePath);
	        for (int i = 0; i < files.size(); i++) {
	            MultipartFile file = files.get(i);

	            if (file == null || file.isEmpty()) {
	                throw new RoomBillzException("File at index " + i + " is missing or empty");
	            }

	            String originalFileName = file.getOriginalFilename();
	            if (originalFileName == null) {
	                throw new RoomBillzException("File name is missing");
	            }

	            String extension = "";
	            if (originalFileName.contains(".")) {
	                extension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1);
	            }

	            Path filePath = path.resolve(originalFileName);
	            Files.write(filePath, file.getBytes());

	            InvoiceDetail invoiceDetail = new InvoiceDetail();
	            invoiceDetail.setFileName(originalFileName);
	            invoiceDetail.setFilePath(filePath.toString());
	            invoiceDetail.setFileSize(file.getSize());
	            invoiceDetail.setExtension(extension);
	            invoiceDetail.setPurchaseOrder(purchaseOrderDetail);

	            invoiceDetailList.add(invoiceDetail);
	        }

	        return invoiceDetailList;
	    } catch (IOException e) {
	        logger.error("File saving failed: {}", e.getMessage(), e);
	        throw new RoomBillzException("Error saving file: " + e.getMessage());
	    } catch (Exception e) {
	        logger.error(RoomContants.ERROR_OCCURRED_DUE_TO, kv("Error Message", e.getMessage()));
	        throw e;
	    }
	}


	
	
}
