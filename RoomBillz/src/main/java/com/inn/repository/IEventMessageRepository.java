package com.inn.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inn.entity.EventMessage;

@Repository
public interface IEventMessageRepository extends JpaRepository<EventMessage, Integer>{
	
    Optional<EventMessage> findFirstByStatusOrderByCreatedAtAsc(String status);


}
