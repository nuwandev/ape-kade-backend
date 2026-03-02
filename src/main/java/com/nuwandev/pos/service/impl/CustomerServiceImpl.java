package com.nuwandev.pos.service.impl;

import com.nuwandev.pos.exception.ResourceNotFoundException;
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
                .orElseThrow(() -> new ResourceNotFoundException("Customer terminal could not locate ID: " + id));
        return customerMapper.toResponseDto(customer);
    }

    @Override
    public void deleteCustomerById(UUID id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Delete failed: Customer node " + id + " not found.");
        }
        customerRepository.deleteCustomerById(id);
    }

    @Override
    public void updateCustomer(UUID id, CustomerRequestDto requestDto) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Update failed: Customer node " + id + " not found.");
        }

        Customer customer = customerMapper.toEntity(requestDto);
        customer.setId(id);
        customerRepository.updateCustomer(id, customer);
    }

    @Override
    public PageResponse<CustomerResponseDto> getCustomers(String q, int page, int size, String sortBy, String direction) {
        List<Customer> customers;
        long totalElements;

        if (q != null && !q.trim().isEmpty()) {
            String query = q.trim();
            customers = customerRepository.searchCustomer(query, page, size, sortBy, direction);
            totalElements = customerRepository.countSearchCustomer(query);
        } else {
            customers = customerRepository.getCustomers(page, size, sortBy, direction);
            totalElements = customerRepository.countCustomers();
        }

        return buildPageResponse(customers, totalElements, page, size);
    }

    private PageResponse<CustomerResponseDto> buildPageResponse(List<Customer> content, long totalElements, int page, int size) {
        int totalPages = (size > 0) ? (int) Math.ceil((double) totalElements / size) : 0;
        return new PageResponse<>(
                customerMapper.toResponseDtoList(content),
                page,
                size,
                totalElements,
                totalPages
        );
    }
}
