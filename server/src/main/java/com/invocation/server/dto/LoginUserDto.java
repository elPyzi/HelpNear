package com.invocation.server.dto;

import lombok.Data;

@Data
public class LoginUserDto {
    private String authString;
    private String password;
}
