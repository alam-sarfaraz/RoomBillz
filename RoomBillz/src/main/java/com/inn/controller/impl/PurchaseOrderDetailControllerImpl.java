package com.inn.controller.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.time.LocalDate;
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
import com.inn.roomConstants.RoomConstants;
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
			logger.info(RoomConstants.INSIDE_THE_METHOD + "createPurchaseOrder {}",kv("PurchaseOrderDetailDto", purchaseOrderDetailDto));
			return iPurchaseOrderDetailService.createPurchaseOrder(purchaseOrderDetailDto, invoiceFiles);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	
	@Override
	@LogRequestResponse
	public ResponseEntity<List<PurchaseOrderDetail>> findAllPurchaseOrderDetail() {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "findAllPurchaseOrderDetail ");
			return iPurchaseOrderDetailService.findAllPurchaseOrderDetail();
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}
	

	@Override
	@LogRequestResponse
	public ResponseEntity<PurchaseOrderDetail> findPurchaseOrderDetailById(Integer id) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "findPurchaseOrderDetailById {}",kv("Id", id));
			return iPurchaseOrderDetailService.findPurchaseOrderDetailById(id);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	@LogRequestResponse
	public ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrderDetailByUserName(String userName) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "findPurchaseOrderDetailByUserName {}",kv("userName", userName));
			return iPurchaseOrderDetailService.findPurchaseOrderDetailByUserName(userName);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	@LogRequestResponse
	public ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrderDetailByGroupName(String groupName) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "findPurchaseOrderDetailByGroupName {}",kv("groupName", groupName));
			return iPurchaseOrderDetailService.findPurchaseOrderDetailByGroupName(groupName);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	@LogRequestResponse
	public ResponseEntity<PurchaseOrderDetail> findPurchaseOrderDetailByPurchaseId(String purchaseId) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "findPurchaseOrderDetailByPurchaseId {}",kv("purchaseId", purchaseId));
			return iPurchaseOrderDetailService.findPurchaseOrderDetailByPurchaseId(purchaseId);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv("Error Message", e.getMessage()));
			throw e;
		}
	}

	@Override
	@LogRequestResponse
	public ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrderDetailByMonth(String month) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "findPurchaseOrderDetailByMonth {}",kv("month", month));
			return iPurchaseOrderDetailService.findPurchaseOrderDetailByMonth(month);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}
	
	@Override
	@LogRequestResponse
	public ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrderDetailByDate(LocalDate date) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "findPurchaseOrderDetailByDate {}",kv("Date", date));
			return iPurchaseOrderDetailService.findPurchaseOrderDetailByDate(date);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}


	@Override
	@LogRequestResponse
	public ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrdByUserAndGroupName(String userName,String groupName) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "findPurchaseOrdByUserAndGroupName {}",kv("UserName", userName),kv("GroupName", groupName));
			return iPurchaseOrderDetailService.findPurchaseOrdByUserAndGroupName(userName,groupName);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}


	@Override
	@LogRequestResponse
	public ResponseEntity<PurchaseOrderDetail> findPurchaseOrdByUserAndPurchaseId(String userName, String purchaseId) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "findPurchaseOrdByUserAndPurchaseId {}",kv("UserName", userName),kv("purchaseId", purchaseId));
			return iPurchaseOrderDetailService.findPurchaseOrdByUserAndPurchaseId(userName,purchaseId);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}


	@Override
	@LogRequestResponse
	public ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrdByUserNameAndDate(String userName, LocalDate date) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "findPurchaseOrdByUserNameAndDate {}",kv("UserName", userName),kv("Date", date));
			return iPurchaseOrderDetailService.findPurchaseOrdByUserNameAndDate(userName,date);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}


	@Override
	@LogRequestResponse
	public ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrdByUserAndGroupAndDate(String userName,String groupName, LocalDate date) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "findPurchaseOrdByUserAndGroupAndDate {}",kv("UserName", userName),kv("GroupName", groupName),kv("Date", date));
			return iPurchaseOrderDetailService.findPurchaseOrdByUserAndGroupAndDate(userName,groupName,date);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}


	@Override
	@LogRequestResponse
	public ResponseEntity<byte[]> downloadPurchaseOrderDetailByPurchaseId(String purchaseId) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "downloadPurchaseOrderDetailByPurchaseId {}",kv("purchaseId", purchaseId));
			return iPurchaseOrderDetailService.downloadPurchaseOrderDetailByPurchaseId(purchaseId);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}


	@Override
	@LogRequestResponse
	public ResponseEntity<ResponseDto> deletePurchaseOrderDetailByPurchaseId(String purchaseId) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "deletePurchaseOrderDetailByPurchaseId {}",kv("purchaseId", purchaseId));
			return iPurchaseOrderDetailService.deletePurchaseOrderDetailByPurchaseId(purchaseId);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}


	@Override
	@LogRequestResponse
	public ResponseEntity<byte[]> exportPODetailByUsernameGroupAndMonthWise(String username, String groupName,String month) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "exportPODetailByUsernameGroupAndMonthWise {}",kv("Username", username),kv("GroupName", groupName),kv("Month", month));
			return iPurchaseOrderDetailService.exportPODetailByUsernameGroupAndMonthWise(username,groupName,month);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}
	
	@Override
	@LogRequestResponse
	public ResponseEntity<byte[]> exportPODetailByUsernameGroupStatusAndMonthWise(String username, String groupName,String status,String month) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "exportPODetailByUsernameGroupStatusAndMonthWise {}",kv("Username", username),kv("GroupName", groupName),kv("Status", status),kv("Month", month));
			return iPurchaseOrderDetailService.exportPODetailByUsernameGroupStatusAndMonthWise(username,groupName,status,month);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	@LogRequestResponse
	public ResponseEntity<byte[]> exportPODetailsByMonth(String month) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "exportPODetailsByMonth {}",kv("Month", month));
			return iPurchaseOrderDetailService.exportPODetailsByMonth(month);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}


	@Override
	@LogRequestResponse
	public ResponseEntity<byte[]> exportPODetailStatus(String status) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "exportPODetailStatus {}",kv("Status", status));
			return iPurchaseOrderDetailService.exportPODetailStatus(status);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}


	@Override
	@LogRequestResponse
	public ResponseEntity<ResponseDto> updatePODetailStatusByPurchaseId(String purchaseId, String status) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "updatePODetailStatusByPurchaseId {}",kv("PurchaseId", purchaseId),kv("Status", status));
			return iPurchaseOrderDetailService.updatePODetailStatusByPurchaseId(purchaseId,status);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}


	@Override
	@LogRequestResponse
	public ResponseEntity<PurchaseOrderDetail> getPurchaseOrderDetailsByCreatedDate() {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "getPurchaseOrderDetailsByCreatedDate");
			return iPurchaseOrderDetailService.getPurchaseOrderDetailsByCreatedDate();
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}


	@Override
	@LogRequestResponse
	public ResponseEntity<ResponseDto> sendMissingPurchaseOrderDetailToNotificationService() {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "sendMissingPurchaseOrderDetailToNotificationService");
			return iPurchaseOrderDetailService.sendMissingPurchaseOrderDetailToNotificationService();
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

}
