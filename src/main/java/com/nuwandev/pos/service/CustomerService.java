package com.nuwandev.pos.service;

import com.nuwandev.pos.model.dto.request.CustomerRequestDto;
import com.nuwandev.pos.model.dto.response.CustomerResponseDto;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<CustomerResponseDto> getAllCustomers();

    void saveCustomer(CustomerRequestDto requestDto);

    CustomerResponseDto getCustomerById(UUID id);

    void deleteCustomerById(UUID id);

    void updateCustomer(UUID id, CustomerRequestDto requestDto);

    List<CustomerResponseDto> searchCustomer(String q);
}
