package com.nuwandev.pos.service.impl;

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
import org.springframework.security.core.userdetails.UserDetails;
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

    public void register(RegisterRequest req) {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setFullName(req.getFullName());
        user.setRole("CASHIER");
        user.setPassword(encoder.encode(req.getPassword()));
        userRepo.save(user);
    }

    public Map<String, Object> login(LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getIdentifier(), req.getPassword())
        );

        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        ResponseCookie cookie = jwtService.generateJwtCookie(userDetails);
        AuthResponse responseDto = new AuthResponse("Login successful", userDetails.getUsername(), "CASHIER");

        return Map.of("cookie", cookie, "response", responseDto);
    }

    public ResponseCookie logout() {
        return jwtService.getCleanJwtCookie();
    }
}
