package com.invocation.server.dto;

import lombok.Data;

@Data
public class ConclusionUserDto {
    String textInfo;
    String title;
    private Professional professional;
    @Data
    public static class Professional{
        String fullName;
        String contactNumber;
    }
}
