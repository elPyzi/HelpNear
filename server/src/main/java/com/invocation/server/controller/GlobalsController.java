package com.invocation.server.controller;

import com.invocation.server.dto.ResponceErrorServerDto;
import com.invocation.server.dto.ResponceProfessionalsDto;
import com.invocation.server.dto.ResponceSupportCenterDto;
import com.invocation.server.dto.ResponceUsersCitizenDto;
import com.invocation.server.service.GlobalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

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

    @PutMapping("/accept-problem/{id}")
    public ResponseEntity<?> acceptProblem(@PathVariable int id) {
        ResponceErrorServerDto errorResponse = globalsService.acceptProblem(id);
        return ResponseEntity.status(HttpStatus.valueOf(errorResponse.getErrorCode())).body(errorResponse);
    }

    @PutMapping("/refuse-problem/{id}")
    public ResponseEntity<?> refuseProblem(@PathVariable int id) {
        ResponceErrorServerDto errorResponse = globalsService.refuseProblem(id);
        return ResponseEntity.status(HttpStatus.valueOf(errorResponse.getErrorCode()))
                .body(Collections.singletonMap("text",
                        "Мы не можем рассмотреть вашу проблему 😔"));
    }

    @GetMapping("/get-users")
    public ResponseEntity<?> getUsersCitizen() {
        ResponceUsersCitizenDto responceUsersCitizenDto = new ResponceUsersCitizenDto();
        ResponceErrorServerDto errorResponse = globalsService.getUsersCitizen(responceUsersCitizenDto);
        return ResponseEntity.status(HttpStatus.valueOf(errorResponse.getErrorCode())).body(responceUsersCitizenDto.getUsers());
    }
}
