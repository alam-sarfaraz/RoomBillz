package com.inn.repository;

import java.time.LocalDate;
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

	List<PurchaseOrderDetail> findByUserNameAndGroupName(String userName, String groupName);

	List<PurchaseOrderDetail> findByPurchaseDate(LocalDate date);

	PurchaseOrderDetail findByUserNameAndPurchaseId(String userName, String purchaseId);

	List<PurchaseOrderDetail> findByUserNameAndPurchaseDate(String userName, LocalDate date);

	List<PurchaseOrderDetail> findByUserNameAndGroupNameAndPurchaseDate(String userName,String groupName, LocalDate date);
	
	List<PurchaseOrderDetail> findByUserNameAndGroupNameAndMonth(String userName,String groupName, String month);
	
	List<PurchaseOrderDetail> findByUserNameAndGroupNameAndStatusAndMonth(String userName,String groupName,String status, String month);

}
