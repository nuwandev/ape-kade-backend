package com.nuwandev.pos.controller;

import com.nuwandev.pos.model.User;
import com.nuwandev.pos.model.dto.request.LoginRequest;
import com.nuwandev.pos.model.dto.request.RegisterRequest;
import com.nuwandev.pos.model.dto.response.ApiResponse;
import com.nuwandev.pos.model.dto.response.AuthResponse;
import com.nuwandev.pos.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody RegisterRequest req) {
        authService.register(req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest req) {
        Map<String, Object> authResult = authService.login(req);

        ResponseCookie cookie = (ResponseCookie) authResult.get("cookie");
        AuthResponse data = (AuthResponse) authResult.get("response");

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(ApiResponse.success("Login successful", data));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout() {
        ResponseCookie cookie = authService.logout();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(ApiResponse.success("Logged out successfully"));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<AuthResponse>> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Not authenticated"));
        }

        User user = (User) authentication.getPrincipal();
        AuthResponse data = new AuthResponse("Session active", user.getUsername(), user.getRole());
        return ResponseEntity.ok(ApiResponse.success("User profile retrieved", data));
    }
}