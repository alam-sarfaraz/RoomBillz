package com.inn.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inn.entity.GroupDetail;

@Repository
public interface IGroupDetailRepository extends JpaRepository<GroupDetail, Integer>{
	
	Optional<GroupDetail> findByGroupName(String groupName);

}
