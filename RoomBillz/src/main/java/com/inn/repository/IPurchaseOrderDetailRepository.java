package com.inn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inn.entity.PurchaseOrderDetail;

@Repository
public interface IPurchaseOrderDetailRepository extends JpaRepository<PurchaseOrderDetail, Integer>{
	
	@Query("SELECT MAX(p.id) FROM PurchaseOrderDetail p")
    Integer findMaxId();

	List<PurchaseOrderDetail> findByUserName(String userName);

	List<PurchaseOrderDetail> findByGroupName(String groupName);

	PurchaseOrderDetail findByPurchaseId(String purchaseId);

	List<PurchaseOrderDetail> findByMonth(String month);

}
