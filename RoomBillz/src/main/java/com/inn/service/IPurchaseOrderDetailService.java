package com.inn.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.inn.dto.PurchaseOrderDetailDto;
import com.inn.dto.ResponseDto;

import jakarta.validation.Valid;

public interface IPurchaseOrderDetailService {

	public ResponseEntity<ResponseDto> createPurchaseOrder(@Valid PurchaseOrderDetailDto purchaseOrderDetailDto,List<MultipartFile> invoiceFiles);

}
