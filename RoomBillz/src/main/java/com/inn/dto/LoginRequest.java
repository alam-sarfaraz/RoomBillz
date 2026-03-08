package com.inn.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "LoginRequest", description = "Data transfer object for LoginRequest")
public class LoginRequest {

	@Schema(description = "Username", example = "sarfarazalam", required = true)
    @NotEmpty(message = "User Name can not be a null or empty")
    @Size(min = 5, max = 30, message = "The length of user name should be between 5 to 30")
    private String username;
	
    @Schema(description = "Password", example = "Secret@123", required = true)
    @NotEmpty(message = "password can not be a null or empty")
    @Size(min = 8, max = 30, message = "The length of password should be between 5 to 30")
    private String password;
    
}