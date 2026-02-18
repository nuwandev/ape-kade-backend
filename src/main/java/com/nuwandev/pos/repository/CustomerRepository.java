package com.nuwandev.pos.repository;

import com.nuwandev.pos.model.Customer;
import com.nuwandev.pos.model.dto.CustomerRequestDto;

import java.util.List;

public interface CustomerRepository {
    List<Customer> getAllCustomers();

    Customer getCustomerById(String id);

    void saveCustomer(CustomerRequestDto requestDto);

    void updateCustomer(String id, CustomerRequestDto requestDto);

    void deleteCustomerById(String id);

    List<Customer> searchCustomer(String q);

}
