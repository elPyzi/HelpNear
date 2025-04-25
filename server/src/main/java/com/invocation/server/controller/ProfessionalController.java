package com.invocation.server.controller;

import com.invocation.server.dto.RequestMakeJudgment;
import com.invocation.server.dto.ResponceErrorServerDto;
import com.invocation.server.dto.ResponceUserProblemId;
import com.invocation.server.dto.ResponceUsersProblemProfessionalDto;
import com.invocation.server.service.ProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping ("/professional")
public class ProfessionalController {
    @Autowired
    private ProfessionalService profService;

    @GetMapping("/get-users-problem")
    public ResponseEntity<?> getUsersProblem(Principal principal) {
        System.out.println(principal.getName() + " adadadadada");
        ResponceUsersProblemProfessionalDto responceUsersProblemProfessionalDto = new ResponceUsersProblemProfessionalDto();
        ResponceErrorServerDto errorResponse = profService.getUsersProfessionalProblem(principal.getName(), responceUsersProblemProfessionalDto);
        return ResponseEntity.status(HttpStatus.valueOf(errorResponse.getErrorCode())).body(responceUsersProblemProfessionalDto.getUsersProblemProfessionals());
    }

    @GetMapping("/get-users-problem/{id}")
    public ResponseEntity<?> getUserProblemId(@PathVariable int id) {
        ResponceUserProblemId responceUserProblemId = new ResponceUserProblemId();
        ResponceErrorServerDto errorResponse = profService.getUserProblemId(id, responceUserProblemId);
        return ResponseEntity.status(HttpStatus.valueOf(errorResponse.getErrorCode())).body(responceUserProblemId);
    }

    @PostMapping("/make-judgment")
    public ResponseEntity<?> makeJudgment(@RequestBody RequestMakeJudgment requestMakeJudgment, Principal principal) {
        ResponceErrorServerDto errorResponse = profService.makeJudgment(requestMakeJudgment, principal.getName());
        return ResponseEntity.status(HttpStatus.valueOf(errorResponse.getErrorCode())).body(errorResponse);
    }
}
