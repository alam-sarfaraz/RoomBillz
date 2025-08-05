package com.inn.roomUtility;

import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import com.inn.customException.RoomBillzException;

public class RoomUtility {

	public static String generateCode() {
		Random random = new Random();
		int number = random.nextInt(100000);
		return String.format("GR-%05d", number);
	}
	
	public static String generateUserId(String firstName,String lastName) {
		if(StringUtils.isNotEmpty(firstName) && StringUtils.isNotEmpty(lastName)) {
			String first = firstName.substring(0, 4).toLowerCase();
			String last = lastName.substring(0, 4).toLowerCase();
			return first+last;
		}else {
			Random random = new Random();
			int number = random.nextInt(100000);
			return String.format("USER-%05d", number);
		}
	}


	public static void validateEmail(String email, String confirmEmail) {
	    if (email == null || confirmEmail == null) {
	        throw new RoomBillzException("Email and confirmEmail must not be null");
	    }

	    if (!email.equals(confirmEmail)) {
	        throw new RoomBillzException("Email and confirmEmail do not match");
	    }
	    
	    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
	    if (!email.matches(emailRegex)) {
	        throw new RoomBillzException("Invalid email format");
	    }
	}


	public static void validatePassword(String password, String confirmPassword) {
	    if (password == null || confirmPassword == null) {
	        throw new RoomBillzException("Password and confirmPassword must not be null");
	    }

	    if (!password.equals(confirmPassword)) {
	        throw new RoomBillzException("Password and confirmPassword do not match");
	    }
	    
	    String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
	    if (!password.matches(passwordRegex)) {
	        throw new RoomBillzException("Password must be at least 8 characters long and include uppercase, lowercase, number, and special character");
	    }
	}


}
