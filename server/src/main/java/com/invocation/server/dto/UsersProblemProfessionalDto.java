package com.invocation.server.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UsersProblemProfessionalDto {
    int id;
    String fullName;
    String email;
    String birthDate;
    String contactNumber;
    String address;
}
