package com.inn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inn.entity.GroupDetailMapping;

@Repository
public interface IGroupDetailMappingRepository extends JpaRepository<GroupDetailMapping, Integer> {

	boolean existsByGroupName(String groupName);
}
