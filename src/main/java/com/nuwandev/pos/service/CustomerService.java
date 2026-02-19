package com.nuwandev.pos.service;

import com.nuwandev.pos.model.Customer;
import com.nuwandev.pos.model.dto.request.CustomerRequestDto;
import com.nuwandev.pos.model.dto.response.CustomerResponseDto;

import java.util.List;

public interface CustomerService {

    List<CustomerResponseDto> getAllCustomers();

    void saveCustomer(CustomerRequestDto requestDto);

    CustomerResponseDto getCustomerById(String id);

    void deleteCustomerById(String id);

    void updateCustomer(String id, CustomerRequestDto requestDto);

    List<CustomerResponseDto> searchCustomer(String q);
}
