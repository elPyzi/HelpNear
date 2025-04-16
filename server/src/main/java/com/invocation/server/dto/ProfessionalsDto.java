package com.invocation.server.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ProfessionalsDto {
    int id;
    String email;
    String full_name;
    String contact_number;
    String info;
    double professionals_rating;
    String fk_center;
    double center_rating;
}
