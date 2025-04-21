package com.invocation.server.controller;

import com.invocation.server.dto.ResponceErrorServerDto;
import com.invocation.server.dto.ResponceSupportDto;
import com.invocation.server.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PutMapping("/{action}/support/{id}/{idCenter}")
    ResponseEntity<?> addRemove(@PathVariable String action, @PathVariable int id, @PathVariable int idCenter) {
        ResponceErrorServerDto errorResponse = new ResponceErrorServerDto();
        if(action.equals("add")){
            errorResponse = adminService.addSupport(id, idCenter);
        }
        else if(action.equals("remove")){
            errorResponse = adminService.removeCenter(id);
        }
        return ResponseEntity.status(HttpStatus.valueOf(errorResponse.getErrorCode())).body(errorResponse);
    }

    @GetMapping("/get-supports")
    ResponseEntity<?> getSupports() {
        ResponceSupportDto responceSupportDto = new ResponceSupportDto();
        ResponceErrorServerDto errorResponse = adminService.getAllSupports(responceSupportDto);
        return ResponseEntity.status(HttpStatus.valueOf(errorResponse.getErrorCode())).body(responceSupportDto.getSupports());
    }

    @PutMapping("/{action}/{id}")
    ResponseEntity<?> banUnban(@PathVariable String action, @PathVariable int id) {
        ResponceErrorServerDto errorResponse = new ResponceErrorServerDto();
        if(action.equals("ban")){
            errorResponse = adminService.ban(id);
        }
        else if(action.equals("unban")){
            errorResponse = adminService.unban(id);
        }
        return ResponseEntity.status(HttpStatus.valueOf(errorResponse.getErrorCode())).body(errorResponse);
    }
}
