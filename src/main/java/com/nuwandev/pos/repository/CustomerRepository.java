package com.nuwandev.pos.repository;

import com.nuwandev.pos.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    List<Customer> getAllCustomers();

    Optional<Customer> getCustomerById(String id);

    void saveCustomer(Customer customer);

    void updateCustomer(String id, Customer customer);

    void deleteCustomerById(String id);

    List<Customer> searchCustomer(String q);

}
