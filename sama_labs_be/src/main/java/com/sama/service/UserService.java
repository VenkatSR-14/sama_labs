package com.sama.service;

import com.sama.dto.UserProfileDto;
import com.sama.model.User;
import com.sama.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email.trim().toLowerCase());
    }

    @Transactional
    public User registerUser(String email, String rawPassword, String fullName) {
        String normalizedEmail = email.trim().toLowerCase();

        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new IllegalArgumentException("An account with this email already exists: " + normalizedEmail);
        }

        User user = new User(
                normalizedEmail,
                passwordEncoder.encode(rawPassword),
                fullName.trim()
        );

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email.trim().toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
    }

    @Transactional(readOnly = true)
    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public UserProfileDto getUserProfileByEmail(String email) {
        User user = findByEmail(email);
        return mapToDto(user);
    }

    @Transactional(readOnly = true)
    public UserProfileDto getUserProfileById(UUID id) {
        User user = findById(id);
        return mapToDto(user);
    }

    public UserProfileDto mapToDto(User user) {
        return new UserProfileDto(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getRole()
        );
    }
}
