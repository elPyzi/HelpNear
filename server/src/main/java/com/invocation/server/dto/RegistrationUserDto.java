package com.invocation.server.dto;

import lombok.Data;

@Data
public class RegistrationUserDto {
    private String login;
    private String password;
    private String fullName;
    private String email;
    private String address;
    private String contactNumber;
    private String birthDate;
}
