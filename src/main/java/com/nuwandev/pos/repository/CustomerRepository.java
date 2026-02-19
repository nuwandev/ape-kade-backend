package com.nuwandev.pos.repository;

import com.nuwandev.pos.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    List<Customer> getAllCustomers();

    Optional<Customer> getCustomerById(UUID id);

    void saveCustomer(Customer customer);

    void updateCustomer(UUID id, Customer customer);

    void deleteCustomerById(UUID id);

    List<Customer> searchCustomer(String q);

}
