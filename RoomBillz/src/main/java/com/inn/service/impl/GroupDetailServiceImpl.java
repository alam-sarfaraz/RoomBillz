package com.inn.service.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inn.customException.GroupAlreadyExistException;
import com.inn.dto.GroupDetailDto;
import com.inn.dto.ResponseDto;
import com.inn.entity.GroupDetail;
import com.inn.repository.IGroupDetailRepository;
import com.inn.roomConstants.RoomContants;
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
			
			return null;
		} catch (Exception e) {
			logger.error(RoomContants.ERROR_OCCURRED_DUE_TO,kv("Error Message", e.getMessage()));
			throw e;
		}
	}

}
