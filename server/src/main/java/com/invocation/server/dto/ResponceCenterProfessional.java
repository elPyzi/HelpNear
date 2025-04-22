package com.invocation.server.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponceCenterProfessional {
    private Center center;
    List<Professional> professionals;
    @Data
    public static class Center{
        private int id;
        private String name;
        private String address;
        private String contactNumber;
        private String email;
        private double rating;
    }

    @Data
    public static class Professional{
        private String fullName;
        private String info;
        private double rating;
    }
}
