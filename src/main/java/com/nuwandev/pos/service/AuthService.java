package com.nuwandev.pos.service;

import com.nuwandev.pos.model.dto.request.LoginRequest;
import com.nuwandev.pos.model.dto.request.RegisterRequest;
import org.springframework.http.ResponseCookie;

import java.util.Map;

public interface AuthService {
    void register(RegisterRequest req);

    Map<String, Object> login(LoginRequest req);

    ResponseCookie logout();
}
