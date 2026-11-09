package com.sama.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilsTest {

    private JwtUtils jwtUtils;
    // 256-bit secret key in base64
    private final String secret = "4c8b21a86c8f94d9b736e4f3c706856d1f92e35b7194635849d7f6b907153a82";
    private final long expirationMs = 3600000; // 1 hour

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils();
        ReflectionTestUtils.setField(jwtUtils, "jwtSecret", secret);
        ReflectionTestUtils.setField(jwtUtils, "jwtExpirationMs", expirationMs);
    }

    @Test
    void testGenerateTokenAndValidate() {
        String email = "test@sama.com";
        String token = jwtUtils.generateToken(email);

        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(jwtUtils.validateToken(token));
        assertEquals(email, jwtUtils.getEmailFromToken(token));
    }

    @Test
    void testValidateInvalidToken() {
        assertFalse(jwtUtils.validateToken("invalid.token.structure"));
        assertFalse(jwtUtils.validateToken(""));
        assertFalse(jwtUtils.validateToken(null));
    }

    @Test
    void testValidateTamperedToken() {
        String email = "user@sama.com";
        String token = jwtUtils.generateToken(email);
        String tamperedToken = token + "xyz";

        assertFalse(jwtUtils.validateToken(tamperedToken));
    }
}
