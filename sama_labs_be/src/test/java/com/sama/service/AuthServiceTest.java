package com.sama.service;

import com.sama.dto.AuthResponse;
import com.sama.dto.LoginRequest;
import com.sama.dto.SignupRequest;
import com.sama.dto.UserProfileDto;
import com.sama.model.User;
import com.sama.security.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthService authService;

    private User sampleUser;
    private UserProfileDto sampleDto;

    @BeforeEach
    void setUp() {
        sampleUser = new User("user@sama.com", "encoded_password", "Sama User");
        sampleUser.setId(UUID.randomUUID());
        sampleDto = new UserProfileDto(sampleUser.getId(), sampleUser.getEmail(), sampleUser.getFullName(), "ROLE_USER");
    }

    @Test
    void testSignup_Success() {
        SignupRequest request = new SignupRequest();
        request.setEmail("user@sama.com");
        request.setPassword("securePass123");
        request.setFullName("Sama User");

        when(userService.registerUser("user@sama.com", "securePass123", "Sama User")).thenReturn(sampleUser);
        when(jwtUtils.generateToken("user@sama.com")).thenReturn("mocked.jwt.token");
        when(userService.mapToDto(sampleUser)).thenReturn(sampleDto);

        AuthResponse response = authService.signup(request);

        assertNotNull(response);
        assertEquals("mocked.jwt.token", response.getToken());
        assertEquals("Bearer", response.getTokenType());
        assertEquals("user@sama.com", response.getUser().getEmail());
        verify(userService, times(1)).registerUser("user@sama.com", "securePass123", "Sama User");
    }

    @Test
    void testLogin_Success() {
        LoginRequest request = new LoginRequest();
        request.setEmail("user@sama.com");
        request.setPassword("correctPassword");

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user@sama.com");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        when(userService.findByEmail("user@sama.com")).thenReturn(sampleUser);
        when(jwtUtils.generateToken("user@sama.com")).thenReturn("mocked.jwt.token");
        when(userService.mapToDto(sampleUser)).thenReturn(sampleDto);

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("mocked.jwt.token", response.getToken());
        assertEquals("user@sama.com", response.getUser().getEmail());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void testLogin_BadCredentialsThrowsException() {
        LoginRequest request = new LoginRequest();
        request.setEmail("user@sama.com");
        request.setPassword("wrongPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(BadCredentialsException.class, () -> authService.login(request));
        verify(jwtUtils, never()).generateToken(anyString());
    }
}

