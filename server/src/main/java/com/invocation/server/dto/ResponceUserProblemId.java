package com.invocation.server.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ResponceUserProblemId {
    int id;
    String fullName;
    String title;
    String description;
    String receivedDate;
    String status;
    String process;
}
