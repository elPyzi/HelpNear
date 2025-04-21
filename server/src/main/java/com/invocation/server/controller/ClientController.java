package com.invocation.server.controller;

import com.invocation.server.dto.RequestMakeApplication;
import com.invocation.server.dto.ResponceConclusionUserDto;
import com.invocation.server.dto.ResponceErrorServerDto;
import com.invocation.server.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping("/make-application")
    public ResponseEntity<?> application(@RequestBody RequestMakeApplication request, Principal principal) {
        ResponceErrorServerDto errorResponse = clientService.makeApplication(request, principal.getName());
        return ResponseEntity.status(HttpStatus.valueOf(errorResponse.getErrorCode())).body(errorResponse);
    }

    @GetMapping("/conclusion")
    public ResponseEntity<?> getConclusion(Principal principal) {
        ResponceConclusionUserDto responceConclusionUserDto = new ResponceConclusionUserDto();
        ResponceErrorServerDto errorResponse = clientService.getConclusion(responceConclusionUserDto, principal.getName());
        return ResponseEntity.status(HttpStatus.valueOf(errorResponse.getErrorCode())).body(responceConclusionUserDto.getConclusionUsers());
    }
}
