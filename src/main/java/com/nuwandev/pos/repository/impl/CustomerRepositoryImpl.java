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

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Customer> getCustomerById(UUID id) {
        String sql = "SELECT * FROM customer WHERE id = ?";
        List<Customer> customers = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Customer customer = new Customer();
            customer.setId(fromBytes(rs.getBytes("id")));
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
        }, toBytes(id));
        return customers.isEmpty() ? Optional.empty() : Optional.of(customers.get(0));
    }

    @Override
    public void saveCustomer(Customer customer) {
        String sql = "INSERT INTO customer (id, title, name, dob, salary, address, city, province, postal_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                toBytes(customer.getId()),
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
        String sql = "UPDATE customer SET title = ?, name = ?, dob = ?, salary = ?, address = ?, city = ?, province = ?, postal_code = ? WHERE id = ?";
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
                toBytes(id)
        );
    }

    @Override
    public void deleteCustomerById(UUID id) {
        String sql = "DELETE FROM customer WHERE id = ?";
        jdbcTemplate.update(sql, toBytes(id));
    }

    @Override
    public List<Customer> getCustomers(int page, int size, String sortBy, String direction) {
        int offset = page * size;
        String orderBy = (sortBy != null && !sortBy.isEmpty()) ? sortBy : "created_at";
        String dir = (direction != null && direction.equalsIgnoreCase("desc")) ? "DESC" : "ASC";
        String sql = "SELECT * FROM customer ORDER BY " + orderBy + " " + dir + " LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Customer customer = new Customer();
            customer.setId(fromBytes(rs.getBytes("id")));
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
        String orderBy = (sortBy != null && !sortBy.isEmpty()) ? sortBy : "created_at";
        String dir = (direction != null && direction.equalsIgnoreCase("desc")) ? "DESC" : "ASC";
        String sql = "SELECT * FROM customer WHERE name LIKE ? OR city LIKE ? OR province LIKE ? ORDER BY " + orderBy + " " + dir + " LIMIT ? OFFSET ?";
        String likeQuery = "%" + q + "%";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Customer customer = new Customer();
            customer.setId(fromBytes(rs.getBytes("id")));
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

    private static byte[] toBytes(UUID uuid) {
        if (uuid == null) return null;
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        byte[] buffer = new byte[16];
        for (int i = 0; i < 8; i++) buffer[i] = (byte) (msb >>> 8 * (7 - i));
        for (int i = 8; i < 16; i++) buffer[i] = (byte) (lsb >>> 8 * (15 - i));
        return buffer;
    }

    private static UUID fromBytes(byte[] bytes) {
        if (bytes == null || bytes.length != 16) return null;
        long msb = 0, lsb = 0;
        for (int i = 0; i < 8; i++) msb = (msb << 8) | (bytes[i] & 0xff);
        for (int i = 8; i < 16; i++) lsb = (lsb << 8) | (bytes[i] & 0xff);
        return new UUID(msb, lsb);
    }
}
