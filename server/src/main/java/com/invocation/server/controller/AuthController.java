package com.invocation.server.controller;

import com.invocation.server.dto.*;
import com.invocation.server.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/auth/register")
    public ResponseEntity<ResponceErrorServerDto> register(@RequestBody RequestRegistrationDto wrapper) {
        System.out.println("Received wrapper: " + wrapper);
        RegistrationUserDto reg = wrapper.getData();
        System.out.println("Extracted data: " + reg);
        ResponceErrorServerDto response = authService.register(reg);
        if (response.getErrorCode() == 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.valueOf(response.getErrorCode())).body(response);
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody RequestLoginUserDto req, HttpServletResponse httpResponse) {
        ResponseLoginUserDto responseLoginUser = new ResponseLoginUserDto();
        ResponceErrorServerDto errorResponse = authService.login(req, responseLoginUser);

        if (errorResponse.getErrorCode() == 0) {
            Cookie accessTokenCookie = new Cookie("accessToken", responseLoginUser.getToken().getAccessToken());
            accessTokenCookie.setPath("/");
            accessTokenCookie.setMaxAge(900);

            Cookie refreshTokenCookie = new Cookie("refreshToken", responseLoginUser.getToken().getRefreshToken());
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setMaxAge(604800);

            httpResponse.addCookie(accessTokenCookie);
            httpResponse.addCookie(refreshTokenCookie);

            return ResponseEntity.ok(responseLoginUser);
        }
        else {
            return ResponseEntity.status(HttpStatus.valueOf(errorResponse.getErrorCode())).body(errorResponse);
        }
    }
}
