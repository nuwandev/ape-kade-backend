package com.nuwandev.pos.service.impl;

import com.nuwandev.pos.exception.CustomerNotFoundException;
import com.nuwandev.pos.mapper.CustomerMapper;
import com.nuwandev.pos.model.Customer;
import com.nuwandev.pos.model.dto.request.CustomerRequestDto;
import com.nuwandev.pos.model.dto.response.CustomerResponseDto;
import com.nuwandev.pos.repository.CustomerRepository;
import com.nuwandev.pos.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerResponseDto> getAllCustomers() {
        List<Customer> allCustomers = customerRepository.getAllCustomers();
        return customerMapper.toResponseDtoList(allCustomers);
    }

    @Override
    public void saveCustomer(CustomerRequestDto requestDto) {
        customerRepository.saveCustomer(customerMapper.toEntity(requestDto));
    }

    @Override
    public CustomerResponseDto getCustomerById(String id) {
        Customer customer = customerRepository.getCustomerById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        return customerMapper.toResponseDto(customer);
    }

    @Override
    public void deleteCustomerById(String id) {
        customerRepository.deleteCustomerById(id);
    }

    @Override
    public void updateCustomer(String id, CustomerRequestDto requestDto) {
        customerRepository.updateCustomer(id, customerMapper.toEntity(requestDto));
    }

    @Override
    public List<CustomerResponseDto> searchCustomer(String q) {
        List<Customer> customers = customerRepository.searchCustomer(q);
        return customerMapper.toResponseDtoList(customers);
    }
}
