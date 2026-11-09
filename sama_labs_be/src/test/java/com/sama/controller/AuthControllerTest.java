package com.sama.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sama.dto.AuthResponse;
import com.sama.dto.LoginRequest;
import com.sama.dto.SignupRequest;
import com.sama.dto.UserProfileDto;
import com.sama.service.AuthService;
import com.sama.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void testSignup_Success() throws Exception {
        SignupRequest request = new SignupRequest();
        request.setFullName("Venkat Raghavan");
        request.setEmail("venkat@sama.com");
        request.setPassword("securePassword123");

        UserProfileDto profile = new UserProfileDto(UUID.randomUUID(), "venkat@sama.com", "Venkat Raghavan", "ROLE_USER");
        AuthResponse response = new AuthResponse("mocked.jwt.token", profile);

        when(authService.signup(any(SignupRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value("mocked.jwt.token"))
                .andExpect(jsonPath("$.user.email").value("venkat@sama.com"))
                .andExpect(jsonPath("$.user.fullName").value("Venkat Raghavan"));
    }

    @Test
    void testLogin_Success() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("venkat@sama.com");
        request.setPassword("correctPassword");

        UserProfileDto profile = new UserProfileDto(UUID.randomUUID(), "venkat@sama.com", "Venkat Raghavan", "ROLE_USER");
        AuthResponse response = new AuthResponse("mocked.jwt.token", profile);

        when(authService.login(any(LoginRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mocked.jwt.token"))
                .andExpect(jsonPath("$.user.email").value("venkat@sama.com"));
    }

    @Test
    void testLogin_InvalidCredentials() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("venkat@sama.com");
        request.setPassword("wrongPassword");

        when(authService.login(any(LoginRequest.class))).thenThrow(new BadCredentialsException("Bad credentials"));

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Invalid email or password"));
    }

    @Test
    void testSignup_ValidationError_InvalidEmail() throws Exception {
        SignupRequest request = new SignupRequest();
        request.setFullName("Venkat");
        request.setEmail("not-an-email"); // invalid email format
        request.setPassword("123");       // too short (<6)

        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
