package com.nuwandev.pos.service.impl;

import com.nuwandev.pos.exception.DuplicateResourceException;
import com.nuwandev.pos.model.User;
import com.nuwandev.pos.model.dto.request.LoginRequest;
import com.nuwandev.pos.model.dto.request.RegisterRequest;
import com.nuwandev.pos.model.dto.response.AuthResponse;
import com.nuwandev.pos.repository.UserRepository;
import com.nuwandev.pos.service.AuthService;
import com.nuwandev.pos.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authManager;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    @Override
    public void register(RegisterRequest req) {
        if (userRepo.existsByUsername(req.getUsername())) {
            throw new DuplicateResourceException("Username '" + req.getUsername() + "' is already in use.");
        }

        if (userRepo.existsByEmail(req.getEmail())) {
            throw new DuplicateResourceException("Email '" + req.getEmail() + "' is already registered.");
        }

        User user = User.builder()
                .id(UUID.randomUUID())
                .username(req.getUsername())
                .email(req.getEmail())
                .fullName(req.getFullName())
                .password(encoder.encode(req.getPassword()))
                .role("CASHIER")
                .build();

        userRepo.save(user);
    }

    @Override
    public Map<String, Object> login(LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getIdentifier(), req.getPassword())
        );

        User user = (User) auth.getPrincipal();

        ResponseCookie cookie = jwtService.generateJwtCookie(user);

        AuthResponse responseDto = AuthResponse.builder()
                .username(user.getUsername())
                .fullName(user.getFullName())
                .role(user.getRole())
                .build();

        return Map.of("cookie", cookie, "response", responseDto);
    }

    @Override
    public ResponseCookie logout() {
        return jwtService.getCleanJwtCookie();
    }
}