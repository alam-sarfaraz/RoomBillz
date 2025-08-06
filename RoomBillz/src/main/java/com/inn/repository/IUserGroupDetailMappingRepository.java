package com.inn.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inn.entity.UserGroupDetailMapping;

@Repository
public interface IUserGroupDetailMappingRepository extends JpaRepository<UserGroupDetailMapping, Integer> {
	
	boolean existsByUserName(String userName);
	
	Optional<UserGroupDetailMapping> findByUserNameAndGroupDetailMapping_GroupName(String userName, String groupName);
	
	List<UserGroupDetailMapping> findByUserName(String userName);
	
	List<UserGroupDetailMapping> findByGroupDetailMapping_GroupName(String groupName);
	
	List<UserGroupDetailMapping> findByEmail(String email);





}
