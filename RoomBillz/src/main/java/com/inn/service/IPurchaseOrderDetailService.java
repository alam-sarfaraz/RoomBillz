package com.inn.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.inn.dto.PurchaseOrderDetailDto;
import com.inn.dto.ResponseDto;
import com.inn.entity.PurchaseOrderDetail;

import jakarta.validation.Valid;

public interface IPurchaseOrderDetailService {

	public ResponseEntity<ResponseDto> createPurchaseOrder(@Valid PurchaseOrderDetailDto purchaseOrderDetailDto,List<MultipartFile> invoiceFiles);

	public ResponseEntity<List<PurchaseOrderDetail>> findAllPurchaseOrderDetail();

	public ResponseEntity<PurchaseOrderDetail> findPurchaseOrderDetailById(Integer id);

	public ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrderDetailByUserName(String userName);

	public ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrderDetailByGroupName(String groupName);

	public ResponseEntity<PurchaseOrderDetail> findPurchaseOrderDetailByPurchaseId(String purchaseId);

	public ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrderDetailByMonth(String month);
	
	public ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrderDetailByDate(LocalDate date);

	public ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrdByUserAndGroupName(String userName,String groupName);

	public ResponseEntity<PurchaseOrderDetail> findPurchaseOrdByUserAndPurchaseId(String userName, String purchaseId);

	public ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrdByUserNameAndDate(String userName, LocalDate date);

	public ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrdByUserAndGroupAndDate(String userName,String groupName, LocalDate date);

	public ResponseEntity<byte[]> downloadPurchaseOrderDetailByPurchaseId(String purchaseId);

	public ResponseEntity<ResponseDto> deletePurchaseOrderDetailByPurchaseId(String purchaseId);

	public ResponseEntity<byte[]> exportPODetailByUsernameGroupAndMonthWise(String username, String groupName,String month);

	

}
