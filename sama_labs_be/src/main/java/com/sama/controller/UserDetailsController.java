package com.sama.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sama.dto.UpdateFeelingRequest;
import com.sama.dto.UpdateUserDetailsRequest;
import com.sama.dto.UserDetailsDto;
import com.sama.model.User;
import com.sama.service.UserProfileDetailsService;
import com.sama.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserDetailsController {

    private final UserProfileDetailsService userProfileDetailsService;
    private final UserService userService;

    public UserDetailsController(UserProfileDetailsService userProfileDetailsService, UserService userService) {
        this.userProfileDetailsService = userProfileDetailsService;
        this.userService = userService;
    }

    // GET /api/v1/users/me/details (Returns current user's details, ambition & feeling)
    @GetMapping("/me/details")
    public ResponseEntity<UserDetailsDto> getMyDetails(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = userService.findByEmail(userDetails.getUsername());
        UserDetailsDto details = userProfileDetailsService.getUserDetails(user.getId());
        return ResponseEntity.ok(details);
    }

    // PUT /api/v1/users/me/feeling (Daily check-in / updates mood & feeling context anytime)
    @PutMapping("/me/feeling")
    public ResponseEntity<UserDetailsDto> updateFeeling(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
            @Valid @RequestBody UpdateFeelingRequest request) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = userService.findByEmail(userDetails.getUsername());
        UserDetailsDto updated = userProfileDetailsService.updateFeeling(user.getId(), request);
        return ResponseEntity.ok(updated);
    }

    // PUT /api/v1/users/me/details (Update ambition, professions, phone, location)
    @PutMapping("/me/details")
    public ResponseEntity<UserDetailsDto> updateDetails(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
            @RequestBody UpdateUserDetailsRequest request) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = userService.findByEmail(userDetails.getUsername());
        UserDetailsDto updated = userProfileDetailsService.updateUserDetails(user.getId(), request);
        return ResponseEntity.ok(updated);
    }

    // GET /api/v1/users/{id}/details (Public profile details)
    @GetMapping("/{id}/details")
    public ResponseEntity<UserDetailsDto> getDetailsById(@PathVariable UUID id) {
        UserDetailsDto details = userProfileDetailsService.getUserDetails(id);
        return ResponseEntity.ok(details);
    }
}
