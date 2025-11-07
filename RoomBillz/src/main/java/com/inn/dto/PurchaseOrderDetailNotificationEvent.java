package com.inn.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderDetailNotificationEvent {

	private String createdBy;

	private LocalDateTime createdAt;

	private String purchaseId;

	private LocalDate purchaseDate;

	private String userName;

	private String firstName;

	private String middleName;

	private String lastName;

	private String email;

	private String mobileNumber;

	private String groupName;

	private String status;

	private Double totalPrice;

	private String modeOfPayment;

	private String month;
}
