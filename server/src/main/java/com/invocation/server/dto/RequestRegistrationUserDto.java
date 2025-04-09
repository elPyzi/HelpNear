package com.invocation.server.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RequestRegistrationUserDto {
    String login;
    String email;
    String full_name;
    String birth_date;
    String contact_number;
    String address;
    String password;
}
