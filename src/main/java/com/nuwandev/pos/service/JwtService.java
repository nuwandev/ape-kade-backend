package com.nuwandev.pos.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails user);
    String extractUsername(String token);
}
