package com.invocation.server.dto;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class RegistrationUserDto {
    private String login;
    private String email;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("contact_number")
    private String contactNumber;

    private String address;
    private String password;
}
