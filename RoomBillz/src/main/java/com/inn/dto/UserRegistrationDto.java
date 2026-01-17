package com.inn.dto;

import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UserRegistrationDto", description = "Data transfer object for user registration")
public class UserRegistrationDto {

    @Schema(description = "Unique username", example = "sarfarazalam", required = true)
    @NotEmpty(message = "User Name can not be a null or empty")
    @Size(min = 5, max = 30, message = "The length of user name should be between 5 to 30")
    private String userName;

    @Schema(description = "First name of the user", example = "Mohammed", required = true)
    @NotEmpty(message = "FirstName can not be a null or empty")
    @Size(min = 3, max = 30, message = "The length of first name should be between 5 to 30")
    private String firstName;

    @Schema(description = "Middle name of the user", example = "Sarfaraz")
    private String middleName;

    @Schema(description = "Last name of the user", example = "Alam", required = true)
    @NotEmpty(message = "Last Name can not be a null or empty")
    @Size(min = 3, max = 30, message = "The length of last name should be between 5 to 30")
    private String lastName;

    @Schema(description = "Primary email address", example = "sarfarazalam@example.com", required = true)
    @NotEmpty(message = "Email can not be a null or empty")
    @Email(message = "Email should be a valid value")
    private String email;

    @Schema(description = "Confirmation of the primary email", example = "sarfarazalam@example.com", required = true)
    @NotEmpty(message = "Confirm Email can not be a null or empty")
    @Email(message = "Confirm Email should be a valid value")
    private String confirmEmail;

    @Schema(description = "Password", example = "Secret@123", required = true)
    @NotEmpty(message = "password can not be a null or empty")
    @Size(min = 8, max = 30, message = "The length of password should be between 5 to 30")
    private String password;

    @Schema(description = "Confirmation of the password", example = "Secret@123", required = true)
    @NotEmpty(message = "Confirm Password can not be a null or empty")
    @Size(min = 8, max = 30, message = "The length of Confirm password should be between 5 to 30")
    private String confirmPassword;

    @Schema(description = "Date of Birth in YYYY-MM-DD format", example = "1998-02-27")
    private LocalDate dob;

    @Schema(description = "Gender of the user", example = "Male", required = true)
    @NotEmpty(message = "Gender can not be a null or empty")
    private String gender;

    @Schema(description = "Primary mobile number (10 digits)", example = "8226854405", required = true)
    @NotEmpty(message = "Mobile Number can not be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits")
    private String mobileNumber;

    @Schema(description = "Alternate mobile number (10 digits)", example = "8789519442", required = true)
    @NotEmpty(message = "Alternate Mobile Number can not be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Alternate Mobile Number must be 10 digits")
    private String atlMobileNumber;
    
    @NotNull(message = "Roles must not be null")
    @NotEmpty(message = "At least one role must be provided")
    @Schema(description = "List of roles assigned to the user",
            example = "[\"USER\", \"ADMIN\"]",
            allowableValues = {"USER", "ADMIN"})   
    private List<String> roles;
}
