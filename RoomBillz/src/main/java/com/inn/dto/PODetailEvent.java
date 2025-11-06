package com.inn.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PODetailEvent {

	private String purchaseId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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
