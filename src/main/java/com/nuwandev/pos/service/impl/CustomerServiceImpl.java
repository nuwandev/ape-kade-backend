package com.nuwandev.pos.service.impl;

import com.nuwandev.pos.model.Customer;
import com.nuwandev.pos.model.dto.CustomerRequestDto;
import com.nuwandev.pos.repository.CustomerRepository;
import com.nuwandev.pos.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }

    @Override
    public void saveCustomer(CustomerRequestDto requestDto) {
        customerRepository.saveCustomer(requestDto);
    }

    @Override
    public Customer getCustomerById(String id) {
        return null;
    }

    @Override
    public void deleteCustomerById(String id) {
        customerRepository.deleteCustomerById(id);
    }

    @Override
    public void updateCustomer(String id, CustomerRequestDto requestDto) {
        customerRepository.updateCustomer(id, requestDto);
    }

    @Override
    public List<Customer> searchCustomer(String q) {
        return customerRepository.searchCustomer(q);
    }
}
