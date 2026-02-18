package com.nuwandev.pos.repository.impl;

import com.nuwandev.pos.model.Customer;
import com.nuwandev.pos.model.dto.CustomerRequestDto;
import com.nuwandev.pos.repository.CustomerRepository;
import com.nuwandev.pos.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    JdbcTemplate jdbcTemplate;

    @Override
    public List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM customer";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Customer customer = new Customer();
            customer.setId(rs.getString(1));
            customer.setTitle(rs.getString(2));
            customer.setName(rs.getString(3));
            customer.setDob(rs.getDate(4));
            customer.setSalary(rs.getDouble(5));
            customer.setAddress(rs.getString(6));
            customer.setCity(rs.getString(7));
            customer.setProvince(rs.getString(8));
            customer.setPostalCode(rs.getString(9));
            return customer;
        });
    }

    @Override
    public Customer getCustomerById(String id) {
        String sql = "SELECT * FROM customer WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Customer customer = new Customer();
            customer.setId(rs.getString(1));
            customer.setTitle(rs.getString(2));
            customer.setName(rs.getString(3));
            customer.setDob(rs.getDate(4));
            customer.setSalary(rs.getDouble(5));
            customer.setAddress(rs.getString(6));
            customer.setCity(rs.getString(7));
            customer.setProvince(rs.getString(8));
            customer.setPostalCode(rs.getString(9));
            return customer;
        });
    }

    @Override
    public void saveCustomer(CustomerRequestDto requestDto) {
        String sql = "INSERT INTO customer (id, title, name, dob, salary, address, city, province, postal_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                Util.generateCustomerId(),
                requestDto.getTitle(),
                requestDto.getName(),
                requestDto.getDob(),
                requestDto.getSalary(),
                requestDto.getAddress(),
                requestDto.getCity(),
                requestDto.getProvince(),
                requestDto.getPostalCode()
        );
    }

    @Override
    public void updateCustomer(String id, CustomerRequestDto requestDto) {

    }

    @Override
    public void deleteCustomerById(String id) {

    }

    @Override
    public List<Customer> searchCustomer(String q) {
        return null;
    }
}
