package com.nuwandev.pos.repository;

import com.nuwandev.pos.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByIdentifier(String identifier);
    void save(User user);
}
