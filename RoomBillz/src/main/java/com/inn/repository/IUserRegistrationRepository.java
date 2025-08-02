package com.inn.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inn.entity.UserRegistration;

@Repository
public interface IUserRegistrationRepository extends JpaRepository<UserRegistration, Integer>{
	
	Optional<UserRegistration> findByUserName(String userName);
	
	Optional<UserRegistration> findByEmail(String email);
	
	Optional<UserRegistration> findByMobileNumber(String mobileNumber);

}
