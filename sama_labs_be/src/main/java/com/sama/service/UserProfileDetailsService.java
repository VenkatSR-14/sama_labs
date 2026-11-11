package com.sama.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sama.dto.UpdateFeelingRequest;
import com.sama.dto.UpdateUserDetailsRequest;
import com.sama.dto.UserDetailsDto;
import com.sama.model.UserDetails;
import com.sama.repository.UserDetailsRepository;

@Service
public class UserProfileDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    public UserProfileDetailsService(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Transactional(readOnly = true)
    public UserDetailsDto getUserDetails(UUID userId) {
        UserDetails details = userDetailsRepository.findById(userId)
                .orElseGet(() -> UserDetails.builder().userId(userId).build());
        return toDto(details);
    }

    @Transactional
    public UserDetailsDto updateFeeling(UUID userId, UpdateFeelingRequest request) {
        UserDetails details = userDetailsRepository.findById(userId)
                .orElseGet(() -> UserDetails.builder().userId(userId).build());

        details.updateFeeling(request.getCurrentFeeling(), request.getFeelingNote());
        UserDetails saved = userDetailsRepository.save(details);
        return toDto(saved);
    }

    @Transactional
    public UserDetailsDto updateUserDetails(UUID userId, UpdateUserDetailsRequest request) {
        UserDetails details = userDetailsRepository.findById(userId)
                .orElseGet(() -> UserDetails.builder().userId(userId).build());

        if (request.getUserProfessions() != null) {
            details.setUserProfessions(request.getUserProfessions());
        }
        if (request.getUserAmbition() != null) {
            details.setUserAmbition(request.getUserAmbition());
        }
        if (request.getCurrentFeeling() != null) {
            details.updateFeeling(request.getCurrentFeeling(), request.getFeelingNote());
        }
        if (request.getPhNo() != null) {
            details.setPhNo(request.getPhNo());
        }
        if (request.getLocation() != null) {
            details.setLocation(request.getLocation());
        }

        UserDetails saved = userDetailsRepository.save(details);
        return toDto(saved);
    }

    public UserDetailsDto toDto(UserDetails entity) {
        return UserDetailsDto.builder()
                .userId(entity.getUserId())
                .userProfessions(entity.getUserProfessions())
                .userAmbition(entity.getUserAmbition())
                .currentFeeling(entity.getCurrentFeeling())
                .feelingNote(entity.getFeelingNote())
                .feelingUpdatedAt(entity.getFeelingUpdatedAt())
                .phNo(entity.getPhNo())
                .location(entity.getLocation())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
