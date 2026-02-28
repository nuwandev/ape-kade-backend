package com.nuwandev.pos.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails user);

    String extractUsername(String token);

    ResponseCookie generateJwtCookie(UserDetails userDetails);

    String getJwtFromCookies(HttpServletRequest request);

    ResponseCookie getCleanJwtCookie();

    boolean isTokenValid(String token, UserDetails userDetails);
}
