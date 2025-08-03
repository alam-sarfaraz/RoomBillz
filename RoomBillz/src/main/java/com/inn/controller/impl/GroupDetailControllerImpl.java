package com.inn.controller.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.inn.controller.IGroupDetailController;
import com.inn.dto.GroupDetailDto;
import com.inn.dto.ResponseDto;
import com.inn.roomConstants.RoomContants;
import com.inn.service.IGroupDetailService;

@RestController
public class GroupDetailControllerImpl implements IGroupDetailController{
	
	private static final Logger logger = LoggerFactory.getLogger(GroupDetailControllerImpl.class);
	
	@Autowired
	IGroupDetailService iGroupDetailService;

	@Override
	public ResponseEntity<ResponseDto> createGroupDetail(GroupDetailDto groupDetailDto) {
		try {
			logger.info(RoomContants.INSIDE_THE_METHOD + "createGroupDetail {}", kv("GroupDetailDto",groupDetailDto));
			return iGroupDetailService.createGroupDetail(groupDetailDto);
		} catch (Exception e) {
			logger.error(RoomContants.ERROR_OCCURRED_DUE_TO,kv("Error Message", e.getMessage()));
			throw e;
		}
	}

}
