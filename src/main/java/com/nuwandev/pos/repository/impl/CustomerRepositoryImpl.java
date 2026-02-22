package com.nuwandev.pos.repository.impl;

import com.nuwandev.pos.model.Customer;
import com.nuwandev.pos.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private static final List<String> ALLOWED_SORT_COLUMNS = List.of(
            "id", "title", "name", "dob", "salary", "address", "city", "province", "postal_code", "created_at", "updated_at"
    );
    private final JdbcTemplate jdbcTemplate;

    private String validateSortBy(String sortBy) {
        if (sortBy == null || sortBy.isEmpty()) return "created_at";
        return ALLOWED_SORT_COLUMNS.contains(sortBy) ? sortBy : "created_at";
    }

    @Override
    public Optional<Customer> getCustomerById(UUID id) {
        String sql = "SELECT BIN_TO_UUID(id) as id_str, title, name, dob, salary, address, city, province, postal_code, created_at, updated_at FROM customer WHERE id = UUID_TO_BIN(?)";
        List<Customer> customers = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Customer customer = new Customer();
            customer.setId(UUID.fromString(rs.getString("id_str")));
            customer.setTitle(rs.getString("title"));
            customer.setName(rs.getString("name"));
            customer.setDob(rs.getDate("dob"));
            customer.setSalary(rs.getDouble("salary"));
            customer.setAddress(rs.getString("address"));
            customer.setCity(rs.getString("city"));
            customer.setProvince(rs.getString("province"));
            customer.setPostalCode(rs.getString("postal_code"));
            customer.setCreatedAt(rs.getTimestamp("created_at"));
            customer.setUpdatedAt(rs.getTimestamp("updated_at"));
            return customer;
        }, id.toString());
        return customers.isEmpty() ? Optional.empty() : Optional.of(customers.get(0));
    }

    @Override
    public void saveCustomer(Customer customer) {
        String sql = "INSERT INTO customer (id, title, name, dob, salary, address, city, province, postal_code) VALUES (UUID_TO_BIN(?), ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                customer.getId().toString(),
                customer.getTitle(),
                customer.getName(),
                customer.getDob(),
                customer.getSalary(),
                customer.getAddress(),
                customer.getCity(),
                customer.getProvince(),
                customer.getPostalCode()
        );
    }

    @Override
    public void updateCustomer(UUID id, Customer customer) {
        String sql = "UPDATE customer SET title = ?, name = ?, dob = ?, salary = ?, address = ?, city = ?, province = ?, postal_code = ? WHERE id = UUID_TO_BIN(?)";
        jdbcTemplate.update(
                sql,
                customer.getTitle(),
                customer.getName(),
                customer.getDob(),
                customer.getSalary(),
                customer.getAddress(),
                customer.getCity(),
                customer.getProvince(),
                customer.getPostalCode(),
                id.toString()
        );
    }

    @Override
    public void deleteCustomerById(UUID id) {
        String sql = "DELETE FROM customer WHERE id = UUID_TO_BIN(?)";
        jdbcTemplate.update(sql, id.toString());
    }

    @Override
    public List<Customer> getCustomers(int page, int size, String sortBy, String direction) {
        int offset = page * size;
        String orderBy = validateSortBy(sortBy);
        String dir = (direction != null && direction.equalsIgnoreCase("desc")) ? "DESC" : "ASC";
        String sql = "SELECT BIN_TO_UUID(id) as id_str, title, name, dob, salary, address, city, province, postal_code, created_at, updated_at FROM customer ORDER BY " + orderBy + " " + dir + " LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Customer customer = new Customer();
            customer.setId(UUID.fromString(rs.getString("id_str")));
            customer.setTitle(rs.getString("title"));
            customer.setName(rs.getString("name"));
            customer.setDob(rs.getDate("dob"));
            customer.setSalary(rs.getDouble("salary"));
            customer.setAddress(rs.getString("address"));
            customer.setCity(rs.getString("city"));
            customer.setProvince(rs.getString("province"));
            customer.setPostalCode(rs.getString("postal_code"));
            customer.setCreatedAt(rs.getTimestamp("created_at"));
            customer.setUpdatedAt(rs.getTimestamp("updated_at"));
            return customer;
        }, size, offset);
    }

    @Override
    public long countCustomers() {
        String sql = "SELECT COUNT(*) FROM customer";
        return jdbcTemplate.query(sql, rs -> {
            if (rs.next()) return rs.getLong(1);
            return 0L;
        });
    }

    @Override
    public List<Customer> searchCustomer(String q, int page, int size, String sortBy, String direction) {
        int offset = page * size;
        String orderBy = validateSortBy(sortBy);
        String dir = (direction != null && direction.equalsIgnoreCase("desc")) ? "DESC" : "ASC";
        String sql = "SELECT BIN_TO_UUID(id) as id_str, title, name, dob, salary, address, city, province, postal_code, created_at, updated_at FROM customer WHERE name LIKE ? OR city LIKE ? OR province LIKE ? ORDER BY " + orderBy + " " + dir + " LIMIT ? OFFSET ?";
        String likeQuery = "%" + q + "%";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Customer customer = new Customer();
            customer.setId(UUID.fromString(rs.getString("id_str")));
            customer.setTitle(rs.getString("title"));
            customer.setName(rs.getString("name"));
            customer.setDob(rs.getDate("dob"));
            customer.setSalary(rs.getDouble("salary"));
            customer.setAddress(rs.getString("address"));
            customer.setCity(rs.getString("city"));
            customer.setProvince(rs.getString("province"));
            customer.setPostalCode(rs.getString("postal_code"));
            customer.setCreatedAt(rs.getTimestamp("created_at"));
            customer.setUpdatedAt(rs.getTimestamp("updated_at"));
            return customer;
        }, likeQuery, likeQuery, likeQuery, size, offset);
    }

    @Override
    public long countSearchCustomer(String q) {
        String sql = "SELECT COUNT(*) FROM customer WHERE name LIKE ? OR city LIKE ? OR province LIKE ?";
        String likeQuery = "%" + q + "%";
        return jdbcTemplate.query(sql, rs -> {
            if (rs.next()) return rs.getLong(1);
            return 0L;
        }, likeQuery, likeQuery, likeQuery);
    }
}
