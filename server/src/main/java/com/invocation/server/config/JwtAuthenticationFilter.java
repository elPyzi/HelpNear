package com.invocation.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invocation.server.dto.ResponceErrorServerDto;
import com.invocation.server.dto.ResponseLoginUserDto;
import com.invocation.server.service.AuthService;
import com.invocation.server.service.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthService authService;
    private final JWTUtils jwtUtils;
    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(AuthService authService, JWTUtils jwtUtils, ObjectMapper objectMapper) {
        this.authService = authService;
        this.jwtUtils = jwtUtils;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        String authorizationHeader = request.getHeader("Authorization");
        String getToken = null;

        if(authorizationHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }
        else if (authorizationHeader.length() > 6) {
            getToken = authorizationHeader.substring(7);
        }
        else if (authorizationHeader.length() == 6) {
            sendResponse(response, HttpStatus.UNAUTHORIZED.value(), new ResponceErrorServerDto(401));
            return;
        }
        String token = jwtUtils.extractTokenType(getToken);

        if(token.equals("accessToken") && requestUri.equals("/auth/check")){
            ResponseLoginUserDto responseLoginUser = new ResponseLoginUserDto();
            ResponceErrorServerDto errorResponse = authService.checkAccessToken(getToken, responseLoginUser);

            if (errorResponse.getErrorCode() == 401) {
                sendResponse(response, HttpStatus.UNAUTHORIZED.value(), errorResponse);
                return;
            }
            sendResponse(response, HttpStatus.OK.value(), responseLoginUser);
            return;
        }
        else if (token.equals("refreshToken") && requestUri.equals("/auth/refresh")) {
            ResponseLoginUserDto responseLoginUser = new ResponseLoginUserDto();
            ResponceErrorServerDto errorResponse = authService.refreshAccessToken(getToken, responseLoginUser);
            if (errorResponse.getErrorCode() == 401) {
                sendResponse(response, HttpStatus.UNAUTHORIZED.value(), errorResponse);
                return;
            }
            Cookie accessTokenCookie = new Cookie("accessToken", responseLoginUser.getToken().getAccessToken());
            accessTokenCookie.setPath("/");
            accessTokenCookie.setMaxAge(60);
            response.addCookie(accessTokenCookie);

            Cookie refreshTokenCookie = new Cookie("refreshToken", responseLoginUser.getToken().getRefreshToken());
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setMaxAge(604800);
            response.addCookie(refreshTokenCookie);

            errorResponse.setErrorCode(0);
            sendResponse(response, HttpStatus.OK.value(), errorResponse);
            return;
        }
        String role = jwtUtils.extractRole(getToken);
        String email = jwtUtils.extractUsername(getToken);
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(role)
        );
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }

    private void sendResponse(HttpServletResponse response, int status, Object body) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
