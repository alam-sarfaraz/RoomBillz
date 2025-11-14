package com.inn.service.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inn.customException.DuplicateResourceException;
import com.inn.customException.RoomBillzException;
import com.inn.customException.UserAndGroupException;
import com.inn.customException.UserNotFoundException;
import com.inn.dto.ResponseDto;
import com.inn.dto.UserGroupRegistrationDto;
import com.inn.entity.GroupDetail;
import com.inn.entity.GroupDetailMapping;
import com.inn.entity.PurchaseOrderDetail;
import com.inn.entity.UserGroupDetailMapping;
import com.inn.entity.UserRegistration;
import com.inn.repository.IGroupDetailMappingRepository;
import com.inn.repository.IUserGroupDetailMappingRepository;
import com.inn.roomConstants.RoomConstants;
import com.inn.roomUtility.RoomUtility;
import com.inn.service.IGroupDetailService;
import com.inn.service.IUserGroupRegistrationService;
import com.inn.service.IUserRegistrationService;

@Service
public class UserGroupRegistrationServiceImpl implements IUserGroupRegistrationService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserGroupRegistrationServiceImpl.class);
	
	@Autowired
	IUserGroupDetailMappingRepository iUserGroupDetailMappingRepository;
	
	@Autowired
	IGroupDetailMappingRepository iGroupDetailMappingRepository;
	
	@Autowired
	IUserRegistrationService iUserRegistrationService;
	
	@Autowired
	IGroupDetailService iGroupDetailService;
	
	@Value("${download.excelGroupReport.path}")
	private String excelBasePath;
	
	public ResponseEntity<ResponseDto> registerUserWithGroup(UserGroupRegistrationDto userGroupRegistrationDto) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "registerUserWithGroup {}", kv("UserGroupRegistrationDto",userGroupRegistrationDto));
			
			Optional<UserGroupDetailMapping> userGroupDetailMapping = iUserGroupDetailMappingRepository.findByUserNameAndGroupDetailMapping_GroupName(userGroupRegistrationDto.getUserName(),userGroupRegistrationDto.getGroupName());
			logger.info("UserGroupDetailMapping Data {}", kv("UserGroupDetailMapping",userGroupDetailMapping));
			
	        if (!userGroupDetailMapping.isEmpty()) {
	                throw new DuplicateResourceException(userGroupRegistrationDto.getUserName(),userGroupRegistrationDto.getGroupName());
	        }
	        
			UserRegistration userRegistration = iUserRegistrationService.findByUserName(userGroupRegistrationDto.getUserName());
			logger.info("UserRegistration Data {}", kv("UserRegistration",userRegistration));
			ResponseEntity<GroupDetail> groupDetail = iGroupDetailService.findByGroupName(userGroupRegistrationDto.getGroupName());
			logger.info("GroupDetailData {}", kv("GroupDetail",groupDetail));
			if(userRegistration !=null && groupDetail!=null) {
				UserGroupDetailMapping user = new UserGroupDetailMapping();
				user.setUserName(userRegistration.getUserName());
				user.setUserId(userRegistration.getUserId());
				user.setFirstName(userRegistration.getFirstName());
				user.setMiddleName(userRegistration.getMiddleName());
				user.setLastName(userRegistration.getLastName());
				user.setEmail(userRegistration.getEmail());
				user.setMobileNumber(userRegistration.getMobileNumber());
				
				GroupDetailMapping group = new GroupDetailMapping();
		        group.setGroupName(groupDetail.getBody().getGroupName());
		        group.setGroupId(groupDetail.getBody().getGroupId());
		        
		        user.setGroupDetailMapping(group);
		        group.setUserGroupDetailMapping(user);
		        
		        iUserGroupDetailMappingRepository.save(user);
			}else {
				throw new RoomBillzException("Error occurred due to Invalid Username or GroupName.");
			}
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new ResponseDto("201", "User Register in group Successfully..."));
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO,kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	public ResponseEntity<UserGroupDetailMapping> findUserGroupDetailByUserAndGroupName(String userName,
			String groupName) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "findUserGroupDetailByUserAndGroupName {}",
					kv("userName", userName), kv("groupName", groupName));
			UserGroupDetailMapping detailMapping = iUserGroupDetailMappingRepository
					.findByUserNameAndGroupDetailMapping_GroupName(userName, groupName)
					.orElseThrow(() -> new UserAndGroupException(userName, groupName));
			return ResponseEntity.status(HttpStatus.OK).body(detailMapping);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	public ResponseEntity<List<UserGroupDetailMapping>> findUserGroupDetailByUsername(String userName) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "findUserGroupDetailByUsername {}", kv("userName", userName));
			List<UserGroupDetailMapping> userGroupDetailMappingList = iUserGroupDetailMappingRepository
					.findByUserName(userName);
			if (userGroupDetailMappingList != null && userGroupDetailMappingList.isEmpty()) {
				throw new UserNotFoundException("Username", "Username", userName);
			}
			return ResponseEntity.status(HttpStatus.OK).body(userGroupDetailMappingList);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	public ResponseEntity<List<UserGroupDetailMapping>> findUserGroupDetailByGroupName(String groupName) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "findUserGroupDetailByGroupName {}",
					kv("GroupName", groupName));
			List<UserGroupDetailMapping> userGroupDetailMappingList = iUserGroupDetailMappingRepository
					.findByGroupDetailMapping_GroupName(groupName);
			if (userGroupDetailMappingList != null && userGroupDetailMappingList.isEmpty()) {
				throw new UserNotFoundException("GroupName", "GroupName", groupName);
			}
			return ResponseEntity.status(HttpStatus.OK).body(userGroupDetailMappingList);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	public ResponseEntity<List<UserGroupDetailMapping>> findUserGroupDetailByEmail(String email) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "findUserGroupDetailByEmail {}", kv("Email", email));
			List<UserGroupDetailMapping> userGroupDetailMappingList = iUserGroupDetailMappingRepository.findByEmail(email);
			if (userGroupDetailMappingList != null && userGroupDetailMappingList.isEmpty()) {
				throw new UserNotFoundException("Email", "Email", email);
			}
			return ResponseEntity.status(HttpStatus.OK).body(userGroupDetailMappingList);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}

	@Override
	public ResponseEntity<byte[]> exportUserGroupDetailMappingByGroupName(String groupName) {
		try {
			logger.info(RoomConstants.INSIDE_THE_METHOD + "exportUserGroupDetailMappingByGroupName {}",kv("GroupName",groupName));
			List<GroupDetailMapping> groupDetailMappings = iGroupDetailMappingRepository.findByGroupName(groupName);
			if(groupDetailMappings==null || groupDetailMappings.isEmpty()) {
				throw new UserNotFoundException("GroupDetail", "GroupName", groupName);
			}
			return generateExcelReport(groupName,groupDetailMappings);
		} catch (Exception e) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO,kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}
	
	ResponseEntity<byte[]> generateExcelReport(String groupName,List<GroupDetailMapping> groupDetailMappings) {
		logger.info(RoomConstants.INSIDE_THE_METHOD + "generateExcelReport");
	    try {
	      SXSSFWorkbook workbook = null;
	      String downloadedFileName = groupName+"_"+RoomUtility.dateFormatter()+".xlsx";
	      Date date = new Date();
	      SimpleDateFormat sdf = new SimpleDateFormat(RoomConstants.DATE_TIME);
	      String folder = sdf.format(date);
	      String downloadPath = excelBasePath + folder + File.separator;
	      File createFolder = new File(downloadPath);
	      if (!createFolder.exists()) {
	        createFolder.mkdirs();
	      }
	      logger.info("Download Path {}", kv("DownloadPath", downloadPath));
	      ClassPathResource resource = new ClassPathResource("static/ExportUserGroupMappingSampleFile.xlsx");
	      InputStream inputStream = resource.getInputStream();
	      XSSFWorkbook wbTemplate = new XSSFWorkbook(inputStream);
	      workbook = new SXSSFWorkbook(wbTemplate);
	      workbook.setCompressTempFiles(true);
	      SXSSFSheet workSheet = workbook.getSheetAt(0);
	      workSheet.setRandomAccessWindowSize(1000);
	      Integer rowIndex = 1;
	      for (GroupDetailMapping groupDetailMapping : groupDetailMappings) {
	        rowIndex = populateWorkSheetCellData(workSheet, rowIndex, groupDetailMapping);
	      }
	      FileOutputStream outputStream = new FileOutputStream(new File(downloadPath + downloadedFileName));
	      workbook.write(outputStream);
	      outputStream.close();
	      logger.info("Full file path and file name {}", kv("Full path ", downloadPath + downloadedFileName));
	      return RoomUtility.downloadFile(downloadPath + downloadedFileName);
	    } catch (IOException e) {
	        logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()), e);
	        throw new RoomBillzException("Error while generating Excel file");
	    } catch (Exception e) {
	        logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()), e);
	        throw e;
	    }
	  }

	  private Integer populateWorkSheetCellData(SXSSFSheet workSheet, Integer rowIndex,GroupDetailMapping groupDetailMapping) {
		  logger.info(RoomConstants.INSIDE_THE_METHOD+"populateWorkSheetCellData {}",rowIndex);
	    try {
	      Row row = workSheet.getRow(rowIndex);
	      if (row == null) {
	        row = workSheet.createRow(rowIndex);
	      }
	      List<String> cellValueList = new ArrayList<>();
	      cellValueList.add(groupDetailMapping.getId().toString());
	      cellValueList.add(groupDetailMapping.getCreatedAt().toString());
	      cellValueList.add(groupDetailMapping.getUserGroupDetailMapping().getUserName());
	      cellValueList.add(RoomUtility.getFullName(groupDetailMapping.getUserGroupDetailMapping().getFirstName(), groupDetailMapping.getUserGroupDetailMapping().getMiddleName(), groupDetailMapping.getUserGroupDetailMapping().getLastName()));
	      cellValueList.add(groupDetailMapping.getUserGroupDetailMapping().getEmail());
	      cellValueList.add(groupDetailMapping.getGroupName());
	      cellValueList.add(groupDetailMapping.getUserGroupDetailMapping().getMobileNumber());
	      RoomUtility.cellRender(cellValueList, row);
	      return ++rowIndex;
	    } catch (Exception e) {
	    	logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
	      throw e;
	    }
	  }

		@Override
		public ResponseEntity<byte[]> exportUserGroupDetailMappingByUsername(String username) {
			try {
				logger.info("{} exportUserGroupDetailMappingByUsername {}", RoomConstants.INSIDE_THE_METHOD,kv("username", username));
				List<UserGroupDetailMapping> userGroupDetailMappings = iUserGroupDetailMappingRepository.findByUserName(username);
				if (userGroupDetailMappings == null || userGroupDetailMappings.isEmpty()) {
					throw new UserNotFoundException("UserGroupDetailMapping", "username", username);
				}
				List<GroupDetailMapping> groupDetailMappings = userGroupDetailMappings.stream().map(UserGroupDetailMapping::getGroupDetailMapping)
						                                                              .filter(Objects::nonNull)
						                                                              .collect(Collectors.toList());
				if (groupDetailMappings.isEmpty()) {
					throw new RoomBillzException("Group details not found for username: " + username);
				}
				return generateExcelReport(username, groupDetailMappings);

			} catch (UserNotFoundException | RoomBillzException ex) {
				logger.warn("{} Exception: {}", RoomConstants.ERROR_OCCURRED_DUE_TO, ex.getMessage());
				throw ex;
			} catch (Exception e) {
				logger.error("{} Unexpected error occurred", RoomConstants.ERROR_OCCURRED_DUE_TO, e);
				throw e;
			}
		}

		@Override
		public ResponseEntity<List<String>> getUserListByGroupName(String groupName) {
			try {
				logger.info(RoomConstants.INSIDE_THE_METHOD + "getUserListByGroupName {}",kv("GroupName",groupName));
				ResponseEntity<List<UserGroupDetailMapping>> userGroupDetailList = this.findUserGroupDetailByGroupName(groupName);
				List<String> userList = userGroupDetailList.getBody().stream().map(e->e.getUserName()).collect(Collectors.toList());
				return ResponseEntity.status(HttpStatus.OK).body(userList); 
			} catch (Exception e) {
				logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO,kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
				throw e;
			}
		}

		@Override
		public ResponseEntity<List<String>> getEmailListByGroupName(String groupName) {
			try {
				logger.info(RoomConstants.INSIDE_THE_METHOD + "getEmailListByGroupName {}",kv("GroupName",groupName));
				ResponseEntity<List<UserGroupDetailMapping>> userGroupDetailList = this.findUserGroupDetailByGroupName(groupName);
				List<String> userList = userGroupDetailList.getBody().stream().map(e->e.getEmail()).collect(Collectors.toList());
				return ResponseEntity.status(HttpStatus.OK).body(userList); 
			} catch (Exception e) {
				logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO,kv(RoomConstants.ERROR_MESSAGE, e.getMessage()));
				throw e;
			}
		}

}
