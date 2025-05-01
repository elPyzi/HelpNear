package com.invocation.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invocation.server.dto.RegistrationUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthRegistrartionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private RegistrationUserDto registrationUserDto;
    @BeforeEach
    public void setUp() {
        registrationUserDto = new RegistrationUserDto();
        registrationUserDto.setLogin("testUser");
        registrationUserDto.setPassword("password123");
        registrationUserDto.setFullName("Тестовый Пользователь");
        registrationUserDto.setEmail("testuser@example.com");
        registrationUserDto.setAddress("ул. Тестовая, 123");
        registrationUserDto.setContactNumber("+79991234567");
        registrationUserDto.setBirthDate("1990-01-01");
    }
    @Test
    public void testSuccessfulRegistration() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationUserDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.errorCode").value(0));
    }
    @Test
    public void testRegistrationWithExistingUser() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationUserDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.errorCode").value(0));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationUserDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorCode").value(409));
    }
}