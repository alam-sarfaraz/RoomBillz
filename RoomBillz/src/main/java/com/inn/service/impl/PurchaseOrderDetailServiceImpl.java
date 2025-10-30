package com.inn.service.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.inn.customException.PurchaseOrderNotFoundException;
import com.inn.customException.RoomBillzException;
import com.inn.dto.LineItemDetailDto;
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
import com.inn.roomConstants.RoomConstants;
import com.inn.roomUtility.RoomUtility;
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
			logger.info(RoomConstants.INSIDE_THE_METHOD + "createPurchaseOrder {}",kv("PurchaseOrderDetailDto", purchaseOrderDetailDto));
			
			// Validation for user name and group is the part of that group or not.
			iUserGroupDetailMappingRepository.findByUserNameAndGroupDetailMapping_GroupName(purchaseOrderDetailDto.getUserName(),purchaseOrderDetailDto.getGroupName())
	                                      .orElseThrow(() -> new RoomBillzException(String.format("You are not part of the group: '%s'", purchaseOrderDetailDto.getGroupName())));
			
			// Fetching userDetail from DB.
			UserRegistration userRegistration = iUserRegistrationService.findByUserName(purchaseOrderDetailDto.getUserName());
			
			// Fetching groupDetail from DB.
			GroupDetail groupDetail = iGroupDetailService.findByGroupName(purchaseOrderDetailDto.getGroupName()).getBody();
			
			// Creating PurchaseOrderDetail.
			PurchaseOrderDetail purchaseOrderDetail = new PurchaseOrderDetail();
			purchaseOrderDetail.setPurchaseId(generatePurchaseOrderId());
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
			purchaseOrderDetail.setStatus(RoomConstants.PENDING);
			purchaseOrderDetail.setTotalPrice(getTotalPrice(purchaseOrderDetailDto.getItemDetails()));
			purchaseOrderDetail.setModeOfPayment(purchaseOrderDetailDto.getModeOfPayment());
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
			Optional.ofNullable(invoiceFiles)
	        .filter(files -> !files.get(0).isEmpty())
	        .ifPresent(files -> {
	            List<InvoiceDetail> invoiceFileDetailList = setInvoiceFileDetails(files, purchaseOrderDetail);
	            logger.info("Invoice File Detail List {}", kv("invoiceFileDetailList", invoiceFileDetailList));
	            purchaseOrderDetail.setInvoiceDetails(invoiceFileDetailList);
	        });

			purchaseOrderDetail.setLineItemDetails(lineItemDetailList);
			iPurchaseOrderDetailRepository.save(purchaseOrderDetail);
	       return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto("201","Purchase Order saved Successfully."));
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}
	
	// Generate Purchase Id
	public String generatePurchaseOrderId() {
        Integer maxId = iPurchaseOrderDetailRepository.findMaxId();
        int nextId = (maxId == null ? 1 : maxId + 1);
        return String.format("PO-%05d", nextId);
    }
	
	// Total Price
	public Double getTotalPrice(List<LineItemDetailDto> itemDetails) {
		return itemDetails.stream().map(e->e.getItemPrice()).collect(Collectors.summingDouble(e->e));
	}
	
	// Create Invoice Detail
	public List<InvoiceDetail> setInvoiceFileDetails(List<MultipartFile> files, PurchaseOrderDetail purchaseOrderDetail) {
	    try {
	        logger.info(RoomConstants.INSIDE_THE_METHOD + "SetInvoiceFileDetails");

	        if (files == null || files.isEmpty()) {
	            throw new RoomBillzException("File list is missing or empty");
	        }

	        List<InvoiceDetail> invoiceDetailList = new ArrayList<>();
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(RoomConstants.DATE_FORMAT);
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
	        logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
	        throw e;
	    }
	}

	@Override
	public ResponseEntity<List<PurchaseOrderDetail>> findAllPurchaseOrderDetail() {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "findAllPurchaseOrderDetail ");
			List<PurchaseOrderDetail> purchaseOrderDetailList = iPurchaseOrderDetailRepository.findAll();
			
			if (purchaseOrderDetailList == null || purchaseOrderDetailList.isEmpty()) {
				throw new RoomBillzException("Purchase Order Detail not Found.");
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(purchaseOrderDetailList);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	public ResponseEntity<PurchaseOrderDetail> findPurchaseOrderDetailById(Integer id) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "findPurchaseOrderDetailById {}", kv("Id", id));
			PurchaseOrderDetail purchaseOrderDetail = iPurchaseOrderDetailRepository.findById(id)
					                                  .orElseThrow(() -> new PurchaseOrderNotFoundException("Purchase Order Detail", "Id", id.toString()));
			return ResponseEntity.status(HttpStatus.OK).body(purchaseOrderDetail);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	public ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrderDetailByUserName(String userName) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "findPurchaseOrderDetailByUserName {}",
					kv("UserName", userName));
			List<PurchaseOrderDetail> purchaseOrderDetailList = iPurchaseOrderDetailRepository.findByUserName(userName);
			if (purchaseOrderDetailList == null || purchaseOrderDetailList.isEmpty()) {
				throw new PurchaseOrderNotFoundException("Purchase Order Detail", "Username", userName);
			}

			return ResponseEntity.status(HttpStatus.OK).body(purchaseOrderDetailList);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	public ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrderDetailByGroupName(String groupName) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "findPurchaseOrderDetailByGroupName {}",
					kv("GroupName", groupName));
			List<PurchaseOrderDetail> purchaseOrderDetailList = iPurchaseOrderDetailRepository
					.findByGroupName(groupName);
			if (purchaseOrderDetailList == null || purchaseOrderDetailList.isEmpty()) {
				throw new PurchaseOrderNotFoundException("Purchase Order Detail", "GroupName", groupName);
			}
			return ResponseEntity.status(HttpStatus.OK).body(purchaseOrderDetailList);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	public ResponseEntity<PurchaseOrderDetail> findPurchaseOrderDetailByPurchaseId(String purchaseId) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "findPurchaseOrderDetailByPurchaseId {}",
					kv("PurchaseId", purchaseId));
			PurchaseOrderDetail purchaseOrderDetail = iPurchaseOrderDetailRepository.findByPurchaseId(purchaseId);
			if (purchaseOrderDetail == null) {
				throw new PurchaseOrderNotFoundException("Purchase Order Detail", "PurchaseId", purchaseId);
			}
			return ResponseEntity.status(HttpStatus.OK).body(purchaseOrderDetail);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	public ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrderDetailByMonth(String month) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "findPurchaseOrderDetailByMonth {}", kv("Month", month));
			List<PurchaseOrderDetail> purchaseOrderDetailList = iPurchaseOrderDetailRepository.findByMonth(month);
			if (purchaseOrderDetailList == null || purchaseOrderDetailList.isEmpty()) {
				throw new PurchaseOrderNotFoundException("Purchase Order Detail", "Month", month);
			}
			return ResponseEntity.status(HttpStatus.OK).body(purchaseOrderDetailList);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}
	
	@Override
	public ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrderDetailByDate(LocalDate date) {
		try {
		logger.info(RoomConstants.INSIDE_THE_METHOD + "findPurchaseOrderDetailByDate {}", kv("date", date));
		List<PurchaseOrderDetail> purchaseOrderDetailList = iPurchaseOrderDetailRepository.findByPurchaseDate(date);
		if (purchaseOrderDetailList == null || purchaseOrderDetailList.isEmpty()) {
			throw new PurchaseOrderNotFoundException("Purchase Order Detail", "Date", date.toString());
		}
		return ResponseEntity.status(HttpStatus.OK).body(purchaseOrderDetailList);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	public ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrdByUserAndGroupName(String userName,String groupName) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "findPurchaseOrdByUserAndGroupName {}",kv("UserName", userName),kv("GroupName", groupName));
			List<PurchaseOrderDetail> purchaseOrderDetailList = iPurchaseOrderDetailRepository.findByUserNameAndGroupName(userName,groupName);
			if (purchaseOrderDetailList == null || purchaseOrderDetailList.isEmpty()) {
				 throw new RoomBillzException("Purchase Order Detail not Found.");
			}
			return ResponseEntity.status(HttpStatus.OK).body(purchaseOrderDetailList);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	public ResponseEntity<PurchaseOrderDetail> findPurchaseOrdByUserAndPurchaseId(String userName, String purchaseId) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "findPurchaseOrdByUserAndPurchaseId {}",kv("UserName", userName),kv("PurchaseId", purchaseId));
			PurchaseOrderDetail purchaseOrderDetail = iPurchaseOrderDetailRepository.findByUserNameAndPurchaseId(userName,purchaseId);
			if (purchaseOrderDetail == null) {
				throw new RoomBillzException("Purchase Order Detail not Found.");
			}
			return ResponseEntity.status(HttpStatus.OK).body(purchaseOrderDetail);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	public ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrdByUserNameAndDate(String userName, LocalDate date) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "findPurchaseOrdByUserNameAndDate {}",kv("UserName", userName),kv("Date", date));
			List<PurchaseOrderDetail> purchaseOrderDetailList = iPurchaseOrderDetailRepository.findByUserNameAndPurchaseDate( userName,date);
			if (purchaseOrderDetailList == null || purchaseOrderDetailList.isEmpty()) {
				throw new RoomBillzException("Purchase Order Detail not Found.");
			}
			return ResponseEntity.status(HttpStatus.OK).body(purchaseOrderDetailList);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	public ResponseEntity<List<PurchaseOrderDetail>> findPurchaseOrdByUserAndGroupAndDate(String userName,String groupName, LocalDate date) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "findPurchaseOrdByUserAndGroupAndDate {}",kv("UserName", userName),kv("GroupName", groupName),kv("Date", date));
			List<PurchaseOrderDetail> purchaseOrderDetailList = iPurchaseOrderDetailRepository.findByUserNameAndGroupNameAndPurchaseDate(userName,groupName,date);
			if (purchaseOrderDetailList == null || purchaseOrderDetailList.isEmpty()) {
				throw new RoomBillzException("Purchase Order Detail not Found.");
			}
			return ResponseEntity.status(HttpStatus.OK).body(purchaseOrderDetailList);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	public ResponseEntity<byte[]> downloadPurchaseOrderDetailByPurchaseId(String purchaseId) {
	    try {
	        logger.info("{} downloadPurchaseOrderDetailByPurchaseId {}", RoomConstants.INSIDE_THE_METHOD, kv("purchaseId", purchaseId));
	        PurchaseOrderDetail purchaseOrderDetail = iPurchaseOrderDetailRepository.findByPurchaseId(purchaseId);
	        if (purchaseOrderDetail == null) {
	            throw new PurchaseOrderNotFoundException("Purchase Order Detail", "PurchaseId", purchaseId);
	        }
	        logger.info("PurchaseOrderDetail fetched successfully for {}", kv("purchaseId", purchaseOrderDetail.getPurchaseId()));
	        List<InvoiceDetail> invoiceDetails = purchaseOrderDetail.getInvoiceDetails();
	        logger.info("InvoiceDetail List Size = {}",(invoiceDetails != null ? invoiceDetails.size() : "null"));
	        if (invoiceDetails == null || invoiceDetails.isEmpty()) {
	            throw new PurchaseOrderNotFoundException("Purchase Order Detail does not contain any invoice details","PurchaseId",purchaseId);
	        }
	        return downloadFile(purchaseId, invoiceDetails);
	    } catch (Exception e) {
	        logger.error("{} {}", RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
	        throw e;
	    }
	}


	private ResponseEntity<byte[]> downloadFile(String purchaseId, List<InvoiceDetail> invoiceDetails) {
	    logger.info("{} downloadFile {}", RoomConstants.INSIDE_THE_METHOD, kv("InvoiceDetails", invoiceDetails));
	    HttpHeaders headers = new HttpHeaders();
	    try {
	        // ✅ Single file
	        if (invoiceDetails.size() == 1) {
	            InvoiceDetail invoice = invoiceDetails.get(0);
	            File file = new File(invoice.getFilePath());

	            if (!file.exists()) {
	                logger.error("File not found: {}", file.getAbsolutePath());
	                throw new PurchaseOrderNotFoundException("Invoice file does not exist","File Name",invoice.getFileName());
	            }
	            byte[] fileBytes = Files.readAllBytes(file.toPath());
	            // Detect MIME type automatically
	            MediaType mediaType = MediaTypeFactory.getMediaType(file.getName())
	                                  .orElse(MediaType.APPLICATION_OCTET_STREAM);

	            headers.setContentType(mediaType);
	            headers.setContentDispositionFormData("attachment", file.getName());
	            logger.info("Returning single file: {} ({} bytes)", file.getName(), fileBytes.length);
	            return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
	        }

	        // ✅ Multiple files → Create ZIP in memory
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        try (ZipOutputStream zipOut = new ZipOutputStream(baos)) {
	            for (InvoiceDetail invoice : invoiceDetails) {
	                File file = new File(invoice.getFilePath());
	                if (!file.exists()) {
	                    logger.warn("Skipping missing file: {}", file.getAbsolutePath());
	                    continue;
	                }

	                try (FileInputStream fis = new FileInputStream(file)) {
	                    ZipEntry zipEntry = new ZipEntry(file.getName());
	                    zipOut.putNextEntry(zipEntry);
	                    fis.transferTo(zipOut);
	                    zipOut.closeEntry();
	                }
	            }
	        }
	        byte[] zipBytes = baos.toByteArray();
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	        headers.setContentDispositionFormData("attachment", purchaseId + "_invoices.zip");
	        logger.info("Returning ZIP file for purchaseId={} ({} bytes)", purchaseId, zipBytes.length);
	        return new ResponseEntity<>(zipBytes, headers, HttpStatus.OK);

	    } catch (IOException e) {
	        logger.error("Error while preparing file download: {}", e.getMessage(), e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	@Override
	public ResponseEntity<ResponseDto> deletePurchaseOrderDetailByPurchaseId(String purchaseId) {
	    logger.info("{} deletePurchaseOrderDetailByPurchaseId {}", RoomConstants.INSIDE_THE_METHOD, kv("purchaseId", purchaseId));
	    try {
	        PurchaseOrderDetail purchaseOrderDetail = iPurchaseOrderDetailRepository.findByPurchaseId(purchaseId);
	        if (purchaseOrderDetail == null) {
	            throw new PurchaseOrderNotFoundException("Purchase Order Detail", "PurchaseId", purchaseId);
	        }
	        iPurchaseOrderDetailRepository.delete(purchaseOrderDetail);
	        logger.info("Purchase order deleted successfully for purchaseId: {}", purchaseId);
	        return ResponseEntity
	                .status(HttpStatus.OK)
	                .body(new ResponseDto("200", "Purchase Order deleted successfully."));
	    } catch (PurchaseOrderNotFoundException ex) {
	        logger.warn("Purchase order not found for purchaseId: {}", purchaseId);
	        throw ex; 
	    } catch (Exception e) {
	        logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()), e);
	        throw e;
	    }
	}

	@Override
	public ResponseEntity<byte[]> exportPODetailByUsernameGroupAndMonthWise(String username, String groupName,String month) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "exportPODetailByUsernameGroupAndMonthWise {}",kv("Username", username),kv("GroupName", groupName),kv("Month", month));
			List<PurchaseOrderDetail> purchaseOrderDetails = iPurchaseOrderDetailRepository.findByUserNameAndGroupNameAndMonth(username, groupName, month);
			if (purchaseOrderDetails == null || purchaseOrderDetails.isEmpty()) {
	            throw new RoomBillzException("Purchase Order Details not found");
	        }
			
			
			
			return null;
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}
	
	ResponseEntity<byte[]> generateExcelReport(List<PurchaseOrderDetail> purchaseOrderDetails) throws Exception {
		logger.info(RoomConstants.INSIDE_THE_METHOD + "generateExcelReport");
	    try {
	      SXSSFWorkbook workbook = null;
	      Date date = new Date();
	      SimpleDateFormat sdf = new SimpleDateFormat(RoomConstants.DATE_TIME);
	      String folder = sdf.format(date);
	      String downloadPath = RoomConstants.DOWNLOAD_EXCEL_SHEET_PO_DETAIL_PATH + folder + File.separator;
	      File createFolder = new File(downloadPath);
	      if (!createFolder.exists()) {
	        createFolder.mkdirs();
	      }
	      logger.info("Download Path {}", kv("DownloadPath", downloadPath));
	      File file = ResourceUtils.getFile("classpath:PurchaseOrderSampleFile.xlsx");
	      String sampleFilePath = file.getAbsolutePath();
	      logger.info("sampleFilePath Detail  {}", kv("SampleFilePath", sampleFilePath));
	      String downloadedFileName = "PurchaseOrderDetailResult.xlsx";
	      FileInputStream fileInputStream = new FileInputStream(sampleFilePath);
	      XSSFWorkbook wbTemplate = new XSSFWorkbook(fileInputStream);
	      workbook = new SXSSFWorkbook(wbTemplate);
	      workbook.setCompressTempFiles(true);
	      SXSSFSheet workSheet = workbook.getSheetAt(0);
	      workSheet.setRandomAccessWindowSize(1000);
	      Integer rowIndex = 1;
	      for (PurchaseOrderDetail poDetail : purchaseOrderDetails) {
	        rowIndex = populateWorkSheetCellData(workSheet, rowIndex, poDetail);
	      }
	      FileOutputStream outputStream = new FileOutputStream(new File(downloadPath + downloadedFileName));
	      workbook.write(outputStream);
	      outputStream.close();
	      logger.info("Full file path and file name {}", kv("Full path ", downloadPath + downloadedFileName));
	      return RoomUtility.downloadFile(downloadPath + downloadedFileName);
	    } catch (Exception e) {
	    	logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
	      throw e;
	    }
	  }

	  private Integer populateWorkSheetCellData(SXSSFSheet workSheet, Integer rowIndex,PurchaseOrderDetail poDetail) {
		  logger.info(RoomConstants.INSIDE_THE_METHOD, "populateWorkSheetCellData");
	    try {
	      Row row = workSheet.getRow(rowIndex);
	      if (row == null) {
	        row = workSheet.createRow(rowIndex);
	      }
	      List<String> cellValueList = new ArrayList<>();
	      cellValueList.add(poDetail.getId().toString());
	      cellValueList.add(poDetail.getUserName());
	      cellValueList.add(poDetail.getUserName());
	      cellValueList.add(poDetail.getFirstName());
	      cellValueList.add(poDetail.getEmail());
	      cellValueList.add(poDetail.getMobileNumber());
	      cellValueList.add(poDetail.getPurchaseId());
	      cellValueList.add(poDetail.getPurchaseDate().toString());
	      cellValueList.add(poDetail.getGroupName());
	      cellValueList.add(poDetail.getStatus());
	      cellValueList.add(poDetail.getModeOfPayment());
	      cellValueList.add(poDetail.getMonth());
	      cellValueList.add(poDetail.getTotalPrice().toString());
	      RoomUtility.cellRender(cellValueList, row);
	      return ++rowIndex;
	    } catch (Exception e) {
	    	logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
	      throw e;
	    }
	  }

	
}
