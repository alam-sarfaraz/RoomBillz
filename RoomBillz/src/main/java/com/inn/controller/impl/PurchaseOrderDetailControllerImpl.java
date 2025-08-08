package com.inn.controller.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.inn.controller.IPurchaseOrderDetailController;
import com.inn.dto.PurchaseOrderDetailDto;
import com.inn.dto.ResponseDto;
import com.inn.entity.PurchaseOrderDetail;
import com.inn.logs.LogRequestResponse;
import com.inn.roomConstants.RoomContants;
import com.inn.service.IPurchaseOrderDetailService;

import jakarta.validation.Valid;

@RestController
public class PurchaseOrderDetailControllerImpl implements IPurchaseOrderDetailController{
	
	private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderDetailControllerImpl.class);
	
	@Autowired
	IPurchaseOrderDetailService iPurchaseOrderDetailService;

	@Override
	@LogRequestResponse
	public ResponseEntity<ResponseDto> createPurchaseOrder(@Valid PurchaseOrderDetailDto purchaseOrderDetailDto,
			List<MultipartFile> invoiceFiles) {
		try {
			logger.info(RoomContants.INSIDE_THE_METHOD + "createPurchaseOrder {}",kv("PurchaseOrderDetailDto", purchaseOrderDetailDto));
			return iPurchaseOrderDetailService.createPurchaseOrder(purchaseOrderDetailDto, invoiceFiles);
		} catch (Exception e) {
			logger.error(RoomContants.ERROR_OCCURRED_DUE_TO, kv("Error Message", e.getMessage()));
			throw e;
		}
	}

	
	@Override
	@LogRequestResponse
	public ResponseEntity<List<PurchaseOrderDetail>> findAllPurchaseOrderDetail() {
		try {
			logger.info(RoomContants.INSIDE_THE_METHOD + "findAllPurchaseOrderDetail ");
			return iPurchaseOrderDetailService.findAllPurchaseOrderDetail();
		} catch (Exception e) {
			logger.error(RoomContants.ERROR_OCCURRED_DUE_TO, kv("Error Message", e.getMessage()));
			throw e;
		}
	}
	

	@Override
	@LogRequestResponse
	public ResponseEntity<PurchaseOrderDetail> findPurchaseOrderDetailById(Integer id) {
		try {
			logger.info(RoomContants.INSIDE_THE_METHOD + "findPurchaseOrderDetailById {}",kv("Id", id));
			return iPurchaseOrderDetailService.findPurchaseOrderDetailById(id);
		} catch (Exception e) {
			logger.error(RoomContants.ERROR_OCCURRED_DUE_TO, kv("Error Message", e.getMessage()));
			throw e;
		}
	}

	@Override
	@LogRequestResponse
	public ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrderDetailByUserName(String userName) {
		try {
			logger.info(RoomContants.INSIDE_THE_METHOD + "findPurchaseOrderDetailByUserName {}",kv("userName", userName));
			return iPurchaseOrderDetailService.findPurchaseOrderDetailByUserName(userName);
		} catch (Exception e) {
			logger.error(RoomContants.ERROR_OCCURRED_DUE_TO, kv("Error Message", e.getMessage()));
			throw e;
		}
	}

	@Override
	@LogRequestResponse
	public ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrderDetailByGroupName(String groupName) {
		try {
			logger.info(RoomContants.INSIDE_THE_METHOD + "findPurchaseOrderDetailByGroupName {}",kv("groupName", groupName));
			return iPurchaseOrderDetailService.findPurchaseOrderDetailByGroupName(groupName);
		} catch (Exception e) {
			logger.error(RoomContants.ERROR_OCCURRED_DUE_TO, kv("Error Message", e.getMessage()));
			throw e;
		}
	}

	@Override
	@LogRequestResponse
	public ResponseEntity<PurchaseOrderDetail> findPurchaseOrderDetailByPurchaseId(String purchaseId) {
		try {
			logger.info(RoomContants.INSIDE_THE_METHOD + "findPurchaseOrderDetailByPurchaseId {}",kv("purchaseId", purchaseId));
			return iPurchaseOrderDetailService.findPurchaseOrderDetailByPurchaseId(purchaseId);
		} catch (Exception e) {
			logger.error(RoomContants.ERROR_OCCURRED_DUE_TO, kv("Error Message", e.getMessage()));
			throw e;
		}
	}

	@Override
	@LogRequestResponse
	public ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrderDetailByMonth(String month) {
		try {
			logger.info(RoomContants.INSIDE_THE_METHOD + "findPurchaseOrderDetailByMonth {}",kv("month", month));
			return iPurchaseOrderDetailService.findPurchaseOrderDetailByMonth(month);
		} catch (Exception e) {
			logger.error(RoomContants.ERROR_OCCURRED_DUE_TO, kv("Error Message", e.getMessage()));
			throw e;
		}
	}

}
