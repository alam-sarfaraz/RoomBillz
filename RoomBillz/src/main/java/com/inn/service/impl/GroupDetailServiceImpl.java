package com.inn.service.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inn.customException.GroupAlreadyExistException;
import com.inn.customException.GroupNotFoundException;
import com.inn.customException.RoomBillzException;
import com.inn.dto.GroupDetailDto;
import com.inn.dto.ResponseDto;
import com.inn.entity.GroupDetail;
import com.inn.repository.IGroupDetailRepository;
import com.inn.roomConstants.RoomContants;
import com.inn.roomUtility.RoomUtility;
import com.inn.service.IGroupDetailService;

@Service
public class GroupDetailServiceImpl implements IGroupDetailService{
	
	private static final Logger logger = LoggerFactory.getLogger(GroupDetailServiceImpl.class);
	
	@Autowired
	IGroupDetailRepository iGroupDetailRepository;
	
	@Override
	public ResponseEntity<ResponseDto> createGroupDetail(GroupDetailDto groupDetailDto) {
		try {
			logger.info(RoomContants.INSIDE_THE_METHOD + "createGroupDetail {}", kv("GroupDetailDto",groupDetailDto));
			Optional<GroupDetail> groupName = iGroupDetailRepository.findByGroupName(groupDetailDto.getGroupName());
			if(groupName.isPresent()) {
				throw new GroupAlreadyExistException(String.format("%s group detail already exist.",groupDetailDto.getGroupName()));
			}
			GroupDetail groupDetail = mapToGroupDetail(groupDetailDto, new GroupDetail());
			iGroupDetailRepository.save(groupDetail);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new ResponseDto("201", "UserRegistration Successfully..."));
		} catch (Exception e) {
			logger.error(RoomContants.ERROR_OCCURRED_DUE_TO,kv("Error Message", e.getMessage()));
			throw e;
		}
	}
	
	public GroupDetail mapToGroupDetail(GroupDetailDto groupDetailDto,GroupDetail groupDetail) {
		logger.info(RoomContants.INSIDE_THE_METHOD + "mapToGroupDetail {}",kv("GroupDetailDto",groupDetailDto));
		groupDetail.setGroupName(groupDetailDto.getGroupName());
		String groupId = RoomUtility.generateCode();
		groupDetail.setGroupId(groupId);
		groupDetail.setIsDeleted(true);
		return groupDetail;
	}
	

	@Override
	public ResponseEntity<GroupDetail> findGroupDetailById(Integer id) {
		try {
			logger.info(RoomContants.INSIDE_THE_METHOD + "findGroupDetailById {}", kv("Id", id));
			 GroupDetail groupDetail = iGroupDetailRepository.findById(id)
					.orElseThrow(() -> new GroupNotFoundException("Group Detail", "Id", id.toString()));
			 return ResponseEntity.status(HttpStatus.OK)
						.body(groupDetail);
		} catch (Exception e) {
			logger.error(RoomContants.ERROR_OCCURRED_DUE_TO, kv("Error Message", e.getMessage()));
			throw e;
		}
	}

	@Override
	public ResponseEntity<GroupDetail> findByGroupName(String groupName) {
		try {
			logger.info(RoomContants.INSIDE_THE_METHOD + "findByUserName {}", kv("GroupName", groupName));
			GroupDetail groupDetail = iGroupDetailRepository.findByGroupName(groupName)
					.orElseThrow(() -> new GroupNotFoundException("Group Detail", "GroupName", groupName));
			 return ResponseEntity.status(HttpStatus.OK)
						.body(groupDetail);
		} catch (Exception e) {
			logger.error(RoomContants.ERROR_OCCURRED_DUE_TO, kv("Error Message", e.getMessage()));
			throw e;
		}
	}
	
	@Override
	public ResponseEntity<ResponseDto> deleteGroupById(Integer id) {
		logger.info(RoomContants.INSIDE_THE_METHOD + "deleteGroupById {}",kv("Id",id));
		try {
			if (!iGroupDetailRepository.existsById(id)) {
				throw new GroupNotFoundException("Group Detail", "Id", id.toString());
			}
			iGroupDetailRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto("200", "Group Deleted Successfully..."));
		} catch (Exception e) {
			logger.error(RoomContants.ERROR_OCCURRED_DUE_TO,kv("Error Message", e.getMessage()));
			throw e;
		}
	}
	
	@Override
	public ResponseEntity<ResponseDto> deleteByGroupName(String groupName) {
		logger.info(RoomContants.INSIDE_THE_METHOD + "deleteByGroupName {}", kv("GroupName", groupName));
		try {
			GroupDetail groupDetail = iGroupDetailRepository.findByGroupName(groupName)
					.orElseThrow(() -> new GroupNotFoundException("Group Detail", "GroupName", groupName));
			iGroupDetailRepository.deleteById(groupDetail.getId());
			return ResponseEntity.status(HttpStatus.OK)
					             .body(new ResponseDto("200", "Group Deleted Successfully..."));
		} catch (Exception e) {
			logger.error(RoomContants.ERROR_OCCURRED_DUE_TO, kv("Error Message", e.getMessage()));
			throw e;
		}
	}

	@Override
	public ResponseEntity<List<GroupDetail>> findAllGroupDetail() {
		try {
			logger.info(RoomContants.INSIDE_THE_METHOD + "findAllGroupDetail ");
			List<GroupDetail> groupDetailList = iGroupDetailRepository.findAll();
			if (groupDetailList == null || groupDetailList.isEmpty()) {
				throw new RoomBillzException("No groups are present.");
			}
			return ResponseEntity.status(HttpStatus.OK).body(groupDetailList);
		} catch (Exception e) {
			logger.error(RoomContants.ERROR_OCCURRED_DUE_TO, kv("Error Message", e.getMessage()));
			throw e;
		}
	}
	
	

}
