package com.inn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inn.entity.LineItemDetail;

@Repository
public interface ILineItemDetailRepository extends JpaRepository<LineItemDetail, Integer>{

}
