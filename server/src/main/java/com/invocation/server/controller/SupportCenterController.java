package com.invocation.server.controller;

import com.invocation.server.dto.RequestSetProfessionalDto;
import com.invocation.server.dto.ResponceErrorServerDto;
import com.invocation.server.dto.ResponceProfessionalsDto;
import com.invocation.server.dto.ResponceUsersProblemCenterDto;
import com.invocation.server.service.SupportCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/support-center")
public class SupportCenterController {
    @Autowired
    private SupportCenterService supportCenterService;

    @GetMapping("get-center-professionals")
    public ResponseEntity<?> getProfessionals(Principal principal){
        ResponceProfessionalsDto responceProfessionalsDto = new ResponceProfessionalsDto();
        ResponceErrorServerDto errorResponse = supportCenterService.getProfessionals(responceProfessionalsDto, principal.getName());
        return ResponseEntity.status(HttpStatus.valueOf(errorResponse.getErrorCode())).body(responceProfessionalsDto.getProfessionals());
    }

    @GetMapping("/get-users-problem")
    public ResponseEntity<?> getUsersProblem(Principal principal){
        ResponceUsersProblemCenterDto responceUsersProblemCenterDto = new ResponceUsersProblemCenterDto();
        ResponceErrorServerDto errorResponse = supportCenterService.getUsersProblemCenter(responceUsersProblemCenterDto, principal.getName());
        return ResponseEntity.status(HttpStatus.valueOf(errorResponse.getErrorCode())).body(responceUsersProblemCenterDto.getUsersProblemCenters());
    }

    @PostMapping("/set-professional")
    public ResponseEntity<?> setProfessional(@RequestBody RequestSetProfessionalDto req){
        ResponceErrorServerDto errorResponse = supportCenterService.setProfessionalUser(req);
        return ResponseEntity.status(HttpStatus.valueOf(errorResponse.getErrorCode())).body(errorResponse);
    }

    @PutMapping("/{action}/professionals/{id}")
    public ResponseEntity<?> addRemove(@PathVariable String action, @PathVariable int id, Principal principal){
        ResponceErrorServerDto errorResponse = new ResponceErrorServerDto();
        if(action.equals("add")){
            errorResponse = supportCenterService.addProfessional(id, principal.getName());
        }
        else if(action.equals("remove")){
            errorResponse = supportCenterService.removeProfessional(id);
        }
        return ResponseEntity.status(HttpStatus.valueOf(errorResponse.getErrorCode())).body(errorResponse);
    }
}