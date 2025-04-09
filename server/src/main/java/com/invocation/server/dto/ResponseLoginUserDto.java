package com.invocation.server.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ResponseLoginUserDto {
    String full_name;
    String email;
    String birth_date;
    String contact_number;
    String address;

    @JsonIgnore
    private Token token;
    @Data
    public static class Token {
        private String accessToken;
        private String refreshToken;
    }
}
