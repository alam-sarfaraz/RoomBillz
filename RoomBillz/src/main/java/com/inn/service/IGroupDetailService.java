package com.inn.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.inn.dto.GroupDetailDto;
import com.inn.dto.ResponseDto;
import com.inn.entity.GroupDetail;

public interface IGroupDetailService {

	ResponseEntity<ResponseDto> createGroupDetail(GroupDetailDto groupDetailDto);
	
	public ResponseEntity<GroupDetail> findGroupDetailById(Integer id);
	
	public ResponseEntity<GroupDetail> findByGroupName(String groupName);
	
	public ResponseEntity<ResponseDto> deleteGroupById(Integer id);
	
	public ResponseEntity<ResponseDto> deleteByGroupName(String groupName);

	public ResponseEntity<List<GroupDetail>> findAllGroupDetail();
	

}
