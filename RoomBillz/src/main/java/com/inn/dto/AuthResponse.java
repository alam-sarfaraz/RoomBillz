package com.inn.dto;

import java.util.List;

import lombok.Data;

@Data
public class AuthResponse {

	private Integer id;
    private String userName;
    private String userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String password;
    private Boolean isActive;
    private List<RoleDTO> roles;
}
