package com.invocation.server.controller;

import com.invocation.server.dto.ResponceErrorServerDto;
import com.invocation.server.dto.ResponceProfessionalsDto;
import com.invocation.server.dto.ResponceSupportCenterDto;
import com.invocation.server.service.GlobalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/globals")
public class GlobalsController {
    @Autowired
    private GlobalsService globalsService;

    @GetMapping("/get-professionals")
    public ResponseEntity<?> getProfessionals() {
        ResponceProfessionalsDto responceProfessionalsDto = new ResponceProfessionalsDto();
        ResponceErrorServerDto errorResponse = globalsService.getAllProfessionals(responceProfessionalsDto);
        return ResponseEntity.status(HttpStatus.valueOf(errorResponse.getErrorCode())).body(responceProfessionalsDto.getProfessionals());
    }

    @GetMapping("/get-professionals/homepage")
    public ResponseEntity<?> getProfessionalsHomepage() {
        ResponceProfessionalsDto responceProfessionalsDto = new ResponceProfessionalsDto();
        ResponceErrorServerDto errorResponse = globalsService.getRandomProfessionals(responceProfessionalsDto);
        return ResponseEntity.status(HttpStatus.valueOf(errorResponse.getErrorCode())).body(responceProfessionalsDto.getProfessionals());
    }

    @GetMapping("/get-support-centers")
    public ResponseEntity<?> getSupportCenters() {
        ResponceSupportCenterDto responceSupportCenterDto = new ResponceSupportCenterDto();
        ResponceErrorServerDto errorResponse = globalsService.getCenters(responceSupportCenterDto);
        return ResponseEntity.status(HttpStatus.valueOf(errorResponse.getErrorCode())).body(responceSupportCenterDto.getSupportCenters());
    }
}
