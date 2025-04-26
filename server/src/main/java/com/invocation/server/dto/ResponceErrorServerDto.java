package com.invocation.server.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ResponceErrorServerDto {
    private int errorCode;
    private String message;

    @JsonIgnore
    public ResponceErrorServerDto() {
        this.errorCode = 0;
        this.message = null;
    }

    @JsonIgnore
    public ResponceErrorServerDto(int errorCode) {
        this.errorCode = errorCode;
        this.message = null;
    }

    @JsonIgnore
    public ResponceErrorServerDto(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
