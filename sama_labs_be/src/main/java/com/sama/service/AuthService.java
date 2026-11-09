package com.sama.service;

import com.sama.dto.AuthResponse;
import com.sama.dto.LoginRequest;
import com.sama.dto.SignupRequest;
import com.sama.dto.UserProfileDto;
import com.sama.model.User;
import com.sama.security.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthService(
            UserService userService,
            AuthenticationManager authenticationManager,
            JwtUtils jwtUtils
    ) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public AuthResponse signup(SignupRequest request) {
        User savedUser = userService.registerUser(
                request.getEmail(),
                request.getPassword(),
                request.getFullName()
        );

        String token = jwtUtils.generateToken(savedUser.getEmail());
        UserProfileDto profileDto = userService.mapToDto(savedUser);

        return new AuthResponse(token, profileDto);
    }

    public AuthResponse login(LoginRequest request) {
        String normalizedEmail = request.getEmail().trim().toLowerCase();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        normalizedEmail,
                        request.getPassword()
                )
        );

        String authenticatedEmail = authentication.getName();
        User user = userService.findByEmail(authenticatedEmail);
        String token = jwtUtils.generateToken(user.getEmail());
        UserProfileDto profileDto = userService.mapToDto(user);

        return new AuthResponse(token, profileDto);
    }
}
