package com.invocation.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invocation.server.dto.LoginUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthLoginControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private LoginUserDto loginUserDto;
    @BeforeEach
    public void setUp() {
        loginUserDto = new LoginUserDto();
        loginUserDto.setAuthString("sosiska1");
        loginUserDto.setPassword("123456789");
    }
    @Test
    public void testSuccessfulLoginWithLogin() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").exists())
                .andExpect(jsonPath("$.email").value("sos@gmail.com"))
                .andExpect(jsonPath("$.contactNumber").exists())
                .andExpect(jsonPath("$.address").exists())
                .andExpect(jsonPath("$.role").exists())
                .andExpect(cookie().exists("accessToken"))
                .andExpect(cookie().exists("refreshToken"))
                .andExpect(cookie().maxAge("accessToken", 900))
                .andExpect(cookie().maxAge("refreshToken", 604800));
    }
    @Test
    public void testSuccessfulLoginWithEmail() throws Exception {
        loginUserDto.setAuthString("sos@gmail.com");
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").exists())
                .andExpect(jsonPath("$.email").value("sos@gmail.com"))
                .andExpect(jsonPath("$.contactNumber").exists())
                .andExpect(jsonPath("$.address").exists())
                .andExpect(jsonPath("$.role").exists())
                .andExpect(cookie().exists("accessToken"))
                .andExpect(cookie().exists("refreshToken"));
    }
    @Test
    public void testLoginWithWrongPassword() throws Exception {
        loginUserDto.setPassword("wrongpassword");
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginUserDto)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errorCode").value(401));
    }
    @Test
    public void testLoginWithNonExistentUser() throws Exception {
        loginUserDto.setAuthString("nonexistentuser");
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginUserDto)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errorCode").value(401))
                .andExpect(jsonPath("$.message").value("Некорректный пароль или логин"));
    }
}