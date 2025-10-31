package com.inn.roomUtility;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.inn.customException.RoomBillzException;
import com.inn.roomConstants.RoomConstants;

public class RoomUtility {

	private static final Logger logger = LoggerFactory.getLogger(RoomUtility.class);

	public static String simpleDateFormatter(String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(new Date());

	}

	public static String dateFormatter() {
		return simpleDateFormatter(RoomConstants.DATE_PATTERN);

	}

	public static String timeFormatter() {
		return simpleDateFormatter(RoomConstants.TIME_PATTERN);
	}

	public static String modifiedDateTimeFormatter() {
		return simpleDateFormatter(RoomConstants.MODIFIED_DATE_TIME);
	}

	public static String dateTime() {
		return simpleDateFormatter(RoomConstants.DATE_TIME);
	}

	public static String generateCode() {
		Random random = new Random();
		int number = random.nextInt(100000);
		return String.format("GRP-%05d", number);
	}

	public static String generateUserId(String firstName, String lastName) {
		Random random = new Random();
		int number = random.nextInt(100000);

		if (StringUtils.isNotEmpty(firstName) && StringUtils.isNotEmpty(lastName)) {
			String first = firstName.length() >= 4 ? firstName.substring(0, 4).toLowerCase() : firstName.toLowerCase();
			String last = lastName.length() >= 4 ? lastName.substring(0, 4).toLowerCase() : lastName.toLowerCase();
			return first + last + String.format("%05d", number);
		} else {
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
			throw new RoomBillzException(
					"Password must be at least 8 characters long and include uppercase, lowercase, number, and special character");
		}
	}

	public static void cellRender(List<String> cellValueList, Row row) {
		try {
			Integer columnIndex = 0;
			for (String cellResult : cellValueList) {
				Cell cell = row.createCell(columnIndex++);
				cell.setCellValue(cellResult);
			}
		} catch (Exception ex) {
			logger.error(RoomConstants.ERROR_OCCURRED_DUE_TO, "paymentCellRender {}", ex.getMessage());
		}
	}

	public static ResponseEntity<byte[]> downloadFile(String filePath) {
		logger.info(RoomConstants.INSIDE_THE_METHOD + "downloadFile  :{}", filePath);
		try {
			File file = new File(filePath);
			String fileName = file.getName();
			logger.info("downloadFile fileName : {}", fileName);
			byte[] fileByteArray = org.apache.commons.io.IOUtils.toByteArray(new FileInputStream(file));
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			return new ResponseEntity<>(fileByteArray, headers, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error inside downloadFile : {}", e.getMessage());
			return null;
		}
	}

	public static String getFullName(String firstName, String middleName, String lastName) {
		logger.info(RoomConstants.INSIDE_THE_METHOD + "getFullName");
		StringBuilder fullName = new StringBuilder();

		if (firstName != null && !firstName.trim().isEmpty()) {
			fullName.append(firstName.trim());
		}

		if (middleName != null && !middleName.trim().isEmpty()) {
			if (fullName.length() > 0)
				fullName.append(" ");
			fullName.append(middleName.trim());
		}

		if (lastName != null && !lastName.trim().isEmpty()) {
			if (fullName.length() > 0)
				fullName.append(" ");
			fullName.append(lastName.trim());
		}

		return fullName.toString();
	}
}
