package com.inn.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="USER_REGISTRATION")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistration extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "USER_NAME")
	private String userName;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "MIDDLE_NAME")
	private String middleName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "CONFIRM_EMAIL")
	private String confirmEmail;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "CONFIRM_PASSWORD")
	private String confirmPassword;
	
	@Past(message = "Date of birth must be in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "DATE_OF_BIRTH")
    private LocalDate dob;
	
	@Column(name="GENDER")
	private String gender;
	
	@Column(name="MOBILE_NUMBER")
	private String mobileNumber;
	
	@Column(name="ATL_MOBILE_NUMBER")
	private String atlMobileNumber;
	
	@Column(name="IS_ACTIVE")
	private Boolean isActive;
	
	
	
}
