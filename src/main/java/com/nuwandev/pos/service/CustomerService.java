package com.nuwandev.pos.service;

import com.nuwandev.pos.model.Customer;
import com.nuwandev.pos.model.dto.CustomerRequestDto;

import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomers();

    void saveCustomer(CustomerRequestDto requestDto);

    Customer getCustomerById(String id);

    void deleteCustomerById(String id);

    void updateCustomer(String id, CustomerRequestDto requestDto);

    List<Customer> searchCustomer(String q);
}
