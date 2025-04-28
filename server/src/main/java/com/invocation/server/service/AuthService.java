package com.invocation.server.service;

import com.invocation.server.dto.*;
import com.invocation.server.entity.Roles;
import com.invocation.server.entity.Users;
import com.invocation.server.models.ROLES;
import com.invocation.server.repository.RolesRepo;
import com.invocation.server.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    UsersRepo usersRepo;
    @Autowired
    RolesRepo rolesRepo;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponceErrorServerDto register(RegistrationUserDto registrationRequest) {
        ResponceErrorServerDto response = new ResponceErrorServerDto();
        Optional<Roles> result = rolesRepo.findByRoleName("CITIZEN");

        Optional<Users> existingUser = usersRepo.findByLogin(registrationRequest.getLogin());
        if (existingUser.isPresent()) {
            response.setErrorCode(409);
            return response;
        }

        Users user = new Users();
        user.setEmail(registrationRequest.getEmail());
        user.setRole(result.get());
        user.setLogin(registrationRequest.getLogin());
        user.setFullName(registrationRequest.getFullName());
        user.setBirthDateFromString(registrationRequest.getBirthDate());
        user.setContactNumber(registrationRequest.getContactNumber());
        user.setAddress(registrationRequest.getAddress());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setAvatar(null);
        user.setProblem(null);
        System.out.println(user);

        try {
            usersRepo.save(user);
            response.setErrorCode(0);
            return response;
        }
        catch (DataIntegrityViolationException e) {
            response.setErrorCode(400);
            return response;
        }
        catch (Exception e) {
            response.setErrorCode(500);
            return response;
        }
    }

    public ResponceErrorServerDto login(LoginUserDto loginRequest, ResponseLoginUserDto responseLoginUser) {
        ResponceErrorServerDto response = new ResponceErrorServerDto();
        try {
            Users user = new Users();
            if (loginRequest.getAuthString().contains("@")) {
                user = usersRepo.findByEmail(loginRequest.getAuthString())
                        .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
            }
            else{
                user = usersRepo.findByLogin(loginRequest.getAuthString())
                        .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
            }

            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                String jwt = jwtUtils.generateToken(user);
                String refreshToken = jwtUtils.generateRefreshToken(user);

                responseLoginUser.setFullName(user.getFullName());
                responseLoginUser.setContactNumber(user.getContactNumber());
                responseLoginUser.setAddress(user.getAddress());
                responseLoginUser.setRole(ROLES.fromDbValue(user.getRole().getRoleName()));
                responseLoginUser.setEmail(user.getEmail());

                ResponseLoginUserDto.Token tokenData = new ResponseLoginUserDto.Token();
                tokenData.setAccessToken(jwt);
                tokenData.setRefreshToken(refreshToken);

                responseLoginUser.setToken(tokenData);
                response.setErrorCode(0);
            }
            else {
                response.setErrorCode(401);
            }
            return response;
        }
        catch (UsernameNotFoundException e) {
            return new ResponceErrorServerDto(401, "Некорректный пароль или логин");
        }
        catch (Exception e) {
            response.setErrorCode(500);
            return response;
        }
    }

    public ResponceErrorServerDto checkAccessToken(String accessToken, ResponseLoginUserDto responseLoginUser) {
        ResponceErrorServerDto response = new ResponceErrorServerDto();
        try {
            if (accessToken == null || accessToken.isBlank()) {
                response.setErrorCode(401);
                return response;
            }

            if (jwtUtils.isTokenExpired(accessToken)) {
                response.setErrorCode(401);
                return response;
            }

            String userLogin = jwtUtils.extractUsername(accessToken);
            if (userLogin == null) {
                response.setErrorCode(401);
                return response;
            }

            Users user = usersRepo.findByLogin(userLogin)
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

            if (!jwtUtils.isTokenValid(accessToken, user)) {
                response.setErrorCode(401);
                return response;
            }

            responseLoginUser.setFullName(user.getFullName());
            responseLoginUser.setAddress(user.getAddress());
            responseLoginUser.setContactNumber(user.getContactNumber());
            responseLoginUser.setRole(ROLES.fromDbValue(user.getRole().getRoleName()));
            responseLoginUser.setEmail(user.getEmail());

            response.setErrorCode(0);
            return response;
        }
        catch (Exception e) {
            response.setErrorCode(500);
            return response;
        }
    }

    public ResponceErrorServerDto refreshAccessToken(String refreshToken, ResponseLoginUserDto responseLoginUser) {
        ResponceErrorServerDto response = new ResponceErrorServerDto();
        try {
            if (refreshToken == null || refreshToken.isBlank()) {
                response.setErrorCode(401);
                return response;
            }
            if (jwtUtils.isTokenExpired(refreshToken)) {
                response.setErrorCode(401);
                return response;
            }
            String userLogin = jwtUtils.extractUsername(refreshToken);
            if (userLogin == null) {
                response.setErrorCode(401);
                return response;
            }
            Users user = usersRepo.findByLogin(userLogin)
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
            if (!jwtUtils.isTokenValid(refreshToken, user)) {
                response.setErrorCode(401);
                return response;
            }

            String newAccessToken = jwtUtils.generateToken(user);
            ResponseLoginUserDto.Token tokenData = new ResponseLoginUserDto.Token();
            tokenData.setAccessToken(newAccessToken);
            tokenData.setRefreshToken(refreshToken);
            responseLoginUser.setToken(tokenData);

            response.setErrorCode(0);
            return response;
        }
        catch (Exception e) {
            return null;
        }
    }

}
