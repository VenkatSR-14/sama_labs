package com.sama.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sama.dto.UpdateFeelingRequest;
import com.sama.dto.UpdateUserDetailsRequest;
import com.sama.dto.UserDetailsDto;
import com.sama.model.UserDetails;
import com.sama.repository.UserDetailsRepository;

@ExtendWith(MockitoExtension.class)
class UserProfileDetailsServiceTest {

    @Mock
    private UserDetailsRepository userDetailsRepository;

    @InjectMocks
    private UserProfileDetailsService userProfileDetailsService;

    private UUID sampleUserId;
    private UserDetails sampleDetails;

    @BeforeEach
    void setUp() {
        sampleUserId = UUID.randomUUID();
        sampleDetails = UserDetails.builder()
                .userId(sampleUserId)
                .userProfessions(List.of("Engineer", "Seeker"))
                .userAmbition("Attain mental clarity and self-realization")
                .currentFeeling("Seeking Peace")
                .feelingNote("Morning meditation")
                .build();
    }

    @Test
    void testGetUserDetails_Existing() {
        when(userDetailsRepository.findById(sampleUserId)).thenReturn(Optional.of(sampleDetails));

        UserDetailsDto dto = userProfileDetailsService.getUserDetails(sampleUserId);

        assertNotNull(dto);
        assertEquals(sampleUserId, dto.getUserId());
        assertEquals("Seeking Peace", dto.getCurrentFeeling());
        verify(userDetailsRepository).findById(sampleUserId);
    }

    @Test
    void testUpdateFeeling() {
        when(userDetailsRepository.findById(sampleUserId)).thenReturn(Optional.of(sampleDetails));
        when(userDetailsRepository.save(any(UserDetails.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UpdateFeelingRequest request = UpdateFeelingRequest.builder()
                .currentFeeling("Grateful")
                .feelingNote("Peaceful evening")
                .build();

        UserDetailsDto dto = userProfileDetailsService.updateFeeling(sampleUserId, request);

        assertNotNull(dto);
        assertEquals("Grateful", dto.getCurrentFeeling());
        assertEquals("Peaceful evening", dto.getFeelingNote());
        verify(userDetailsRepository).save(any(UserDetails.class));
    }

    @Test
    void testUpdateUserDetails() {
        when(userDetailsRepository.findById(sampleUserId)).thenReturn(Optional.of(sampleDetails));
        when(userDetailsRepository.save(any(UserDetails.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UpdateUserDetailsRequest request = UpdateUserDetailsRequest.builder()
                .userAmbition("Lead with compassion")
                .location("Bangalore, India")
                .build();

        UserDetailsDto dto = userProfileDetailsService.updateUserDetails(sampleUserId, request);

        assertNotNull(dto);
        assertEquals("Lead with compassion", dto.getUserAmbition());
        assertEquals("Bangalore, India", dto.getLocation());
        verify(userDetailsRepository).save(any(UserDetails.class));
    }
}
