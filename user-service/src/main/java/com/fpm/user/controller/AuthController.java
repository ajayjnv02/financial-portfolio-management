package com.fpm.user.controller;

import com.fpm.user.security.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        log.info("[LOGIN_ATTEMPT] Email: {}", email);

        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            log.info("[AUTH_SUCCESS] Authentication passed for user: {}", email);

            String token = jwtUtil.generateToken(email);
            log.info("[TOKEN_ISSUED] Token generated for user: {}", email);

            return Map.of("token", token);

        } catch (BadCredentialsException e) {
            log.error("[AUTH_FAILED] Invalid credentials for user: {}", email);
            throw e;
        } catch (AuthenticationException e) {
            log.error("[AUTH_FAILED] Authentication error for user: {} - {}", email, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("[INTERNAL_ERROR] Unexpected error during login for user: {}", email, e);
            throw e;
        }
    }
}
