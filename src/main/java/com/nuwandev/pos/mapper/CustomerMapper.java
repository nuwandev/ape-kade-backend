package com.nuwandev.pos.mapper;

import com.nuwandev.pos.model.Customer;
import com.nuwandev.pos.model.dto.request.CustomerRequestDto;
import com.nuwandev.pos.model.dto.response.CustomerResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CustomerMapper {
    CustomerResponseDto toResponseDto(Customer customer);

    List<CustomerResponseDto> toResponseDtoList(List<Customer> allCustomers);

    Customer toEntity(CustomerRequestDto requestDto);
}
