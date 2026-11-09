package com.sama.service;

import com.sama.dto.UserProfileDto;
import com.sama.model.User;
import com.sama.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = new User("venkat@sama.com", "hashed_password", "Venkat Raghavan");
        sampleUser.setId(UUID.randomUUID());
    }

    @Test
    void testRegisterUser_Success() {
        when(userRepository.existsByEmail("venkat@sama.com")).thenReturn(false);
        when(passwordEncoder.encode("plainPassword123")).thenReturn("hashed_password");
        when(userRepository.save(any(User.class))).thenReturn(sampleUser);

        User created = userService.registerUser("Venkat@sama.com", "plainPassword123", "Venkat Raghavan");

        assertNotNull(created);
        assertEquals("venkat@sama.com", created.getEmail());
        verify(passwordEncoder, times(1)).encode("plainPassword123");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUser_DuplicateEmailThrowsException() {
        when(userRepository.existsByEmail("venkat@sama.com")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.registerUser("venkat@sama.com", "password", "Venkat")
        );

        assertTrue(exception.getMessage().contains("already exists"));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testFindByEmail_Success() {
        when(userRepository.findByEmail("venkat@sama.com")).thenReturn(Optional.of(sampleUser));

        User found = userService.findByEmail("venkat@sama.com");

        assertNotNull(found);
        assertEquals("venkat@sama.com", found.getEmail());
    }

    @Test
    void testFindByEmail_NotFound() {
        when(userRepository.findByEmail("unknown@sama.com")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.findByEmail("unknown@sama.com"));
    }

    @Test
    void testGetUserProfileByEmail() {
        when(userRepository.findByEmail("venkat@sama.com")).thenReturn(Optional.of(sampleUser));

        UserProfileDto profile = userService.getUserProfileByEmail("venkat@sama.com");

        assertNotNull(profile);
        assertEquals(sampleUser.getEmail(), profile.getEmail());
        assertEquals(sampleUser.getFullName(), profile.getFullName());
    }
}

