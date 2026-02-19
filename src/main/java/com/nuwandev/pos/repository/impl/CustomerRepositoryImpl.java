package com.nuwandev.pos.repository.impl;

import com.nuwandev.pos.model.Customer;
import com.nuwandev.pos.repository.CustomerRepository;
import com.nuwandev.pos.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    JdbcTemplate jdbcTemplate;

    @Override
    public List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM customer";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Customer customer = new Customer();
            customer.setId(rs.getString("id"));
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
        });
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        String sql = "SELECT * FROM customer WHERE id = ?";
        List<Customer> customers = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Customer customer = new Customer();
            customer.setId(rs.getString("id"));
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
        }, id);
        return customers.isEmpty() ? Optional.empty() : Optional.of(customers.get(0));
    }

    @Override
    public void saveCustomer(Customer customer) {
        String sql = "INSERT INTO customer (id, title, name, dob, salary, address, city, province, postal_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                Util.generateCustomerId(),
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
    public void updateCustomer(String id, Customer customer) {
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
            id
        );
    }

    @Override
    public void deleteCustomerById(String id) {
        String sql = "DELETE FROM customer WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Customer> searchCustomer(String q) {
        String sql = "SELECT * FROM customer WHERE name LIKE ? OR city LIKE ? OR province LIKE ?";
        String likeQuery = "%" + q + "%";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Customer customer = new Customer();
            customer.setId(rs.getString("id"));
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
        }, likeQuery, likeQuery, likeQuery);
    }
}
