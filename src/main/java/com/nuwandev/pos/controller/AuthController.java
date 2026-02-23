package com.nuwandev.pos.controller;

import com.nuwandev.pos.model.User;
import com.nuwandev.pos.model.dto.request.LoginRequest;
import com.nuwandev.pos.model.dto.request.RegisterRequest;
import com.nuwandev.pos.model.dto.response.AuthResponse;
import com.nuwandev.pos.repository.UserRepository;
import com.nuwandev.pos.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authManager;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest req) {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setFullName(req.getFullName());
        user.setRole("CASHIER");
        user.setPassword(encoder.encode(req.getPassword()));
        userRepo.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getIdentifier(), req.getPassword())
        );

        UserDetails user = (UserDetails) auth.getPrincipal();

        ResponseCookie jwtCookie = jwtService.generateJwtCookie(user);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new AuthResponse("Login successful", user.getUsername(), "CASHIER"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie cookie = jwtService.getCleanJwtCookie();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Logged out successfully");
    }
}