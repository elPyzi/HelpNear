package com.invocation.server.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.invocation.server.models.ROLES;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ResponseLoginUserDto {
    String fullName;
    String email;
    String contactNumber;
    String address;
    ROLES role;

    @JsonIgnore
    private Token token;
    @Data
    public static class Token {
        private String accessToken;
        private String refreshToken;
    }
}
