package com.nuwandev.pos.service.impl;

import com.nuwandev.pos.exception.CustomerNotFoundException;
import com.nuwandev.pos.mapper.CustomerMapper;
import com.nuwandev.pos.model.Customer;
import com.nuwandev.pos.model.dto.request.CustomerRequestDto;
import com.nuwandev.pos.model.dto.response.CustomerResponseDto;
import com.nuwandev.pos.model.dto.response.PageResponse;
import com.nuwandev.pos.repository.CustomerRepository;
import com.nuwandev.pos.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public void saveCustomer(CustomerRequestDto requestDto) {
        Customer customer = customerMapper.toEntity(requestDto);
        customer.setId(UUID.randomUUID());
        customerRepository.saveCustomer(customer);
    }

    @Override
    public CustomerResponseDto getCustomerById(UUID id) {
        Customer customer = customerRepository.getCustomerById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        return customerMapper.toResponseDto(customer);
    }

    @Override
    public void deleteCustomerById(UUID id) {
        customerRepository.deleteCustomerById(id);
    }

    @Override
    public void updateCustomer(UUID id, CustomerRequestDto requestDto) {
        customerRepository.updateCustomer(id, customerMapper.toEntity(requestDto));
    }

    @Override
    public PageResponse<CustomerResponseDto> getCustomers(int page, int size, String sortBy, String direction) {
        List<Customer> customers = customerRepository.getCustomers(page, size, sortBy, direction);
        long totalElements = customerRepository.countCustomers();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        List<CustomerResponseDto> content = customerMapper.toResponseDtoList(customers);
        return new PageResponse<>(content, page, size, totalElements, totalPages);
    }

    @Override
    public PageResponse<CustomerResponseDto> searchCustomer(String q, int page, int size, String sortBy, String direction) {
        List<Customer> customers = customerRepository.searchCustomer(q, page, size, sortBy, direction);
        long totalElements = customerRepository.countSearchCustomer(q);
        int totalPages = (int) Math.ceil((double) totalElements / size);
        List<CustomerResponseDto> content = customerMapper.toResponseDtoList(customers);
        return new PageResponse<>(content, page, size, totalElements, totalPages);
    }
}
