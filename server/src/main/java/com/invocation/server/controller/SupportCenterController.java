package com.invocation.server.controller;

import com.invocation.server.dto.ResponceErrorServerDto;
import com.invocation.server.dto.ResponceProfessionalsDto;
import com.invocation.server.service.SupportCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/support-center")
public class SupportCenterController {
    @Autowired
    private SupportCenterService supportCenterService;

    @GetMapping("/get-professionals")
    public ResponseEntity<?> getProfessionals(Principal principal){
        ResponceProfessionalsDto responceProfessionalsDto = new ResponceProfessionalsDto();
        ResponceErrorServerDto errorResponse = supportCenterService.getProfessionals(responceProfessionalsDto, principal.getName());
        return ResponseEntity.status(HttpStatus.valueOf(errorResponse.getErrorCode())).body(responceProfessionalsDto.getProfessionals());
    }
}
