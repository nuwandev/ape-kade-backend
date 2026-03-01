package com.nuwandev.pos.repository.impl;

import com.nuwandev.pos.model.User;
import com.nuwandev.pos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbc;

    public Optional<User> findByIdentifier(String identifier) {
        String sql = "SELECT BIN_TO_UUID(id) as id_str, username, email, password, full_name, role FROM users WHERE username = ? OR email = ?";
        try {
            return Optional.ofNullable(jdbc.queryForObject(sql, (rs, rowNum) -> {
                User user = new User();
                user.setId(UUID.fromString(rs.getString("id_str")));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("full_name"));
                user.setRole(rs.getString("role"));
                return user;
            }, identifier, identifier));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void save(User user) {
        String sql = "INSERT INTO users (id, username, email, password, full_name, role) VALUES (UUID_TO_BIN(?), ?, ?, ?, ?, ?)";
        jdbc.update(
                sql,
                user.getId().toString(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getFullName(),
                user.getRole()
        );
    }

    @Override
    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        Integer count = jdbc.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        Integer count = jdbc.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }
}