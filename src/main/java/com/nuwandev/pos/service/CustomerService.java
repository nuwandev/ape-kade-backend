package com.nuwandev.pos.service;

import com.nuwandev.pos.model.dto.request.CustomerRequestDto;
import com.nuwandev.pos.model.dto.response.CustomerResponseDto;
import com.nuwandev.pos.model.dto.response.PageResponse;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    void saveCustomer(CustomerRequestDto requestDto);

    CustomerResponseDto getCustomerById(UUID id);

    void deleteCustomerById(UUID id);

    void updateCustomer(UUID id, CustomerRequestDto requestDto);

    PageResponse<CustomerResponseDto> searchCustomer(String q, int page, int size, String sortBy, String direction);

    PageResponse<CustomerResponseDto> getCustomers(int page, int size, String sortBy, String direction);
}
