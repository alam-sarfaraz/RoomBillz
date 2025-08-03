package com.inn.service;

import org.springframework.http.ResponseEntity;

import com.inn.dto.GroupDetailDto;
import com.inn.dto.ResponseDto;

public interface IGroupDetailService {

	ResponseEntity<ResponseDto> createGroupDetail(GroupDetailDto groupDetailDto);

}
