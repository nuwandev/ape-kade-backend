package com.nuwandev.pos.controller;

import com.nuwandev.pos.model.Customer;
import com.nuwandev.pos.model.dto.CustomerRequestDto;
import com.nuwandev.pos.model.dto.CustomerResponseDto;
import com.nuwandev.pos.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
        List<Customer> customerList = customerService.getAllCustomers();
        List<CustomerResponseDto> responseList = customerList.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable String id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(toResponseDto(customer));
    }

    @PostMapping
    public ResponseEntity<Void> saveCustomer(@Valid @RequestBody CustomerRequestDto requestDto) {
        customerService.saveCustomer(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable String id, @RequestBody CustomerRequestDto requestDto) {
        customerService.updateCustomer(id, requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomerById(@PathVariable String id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomerResponseDto>> searchCustomer(@RequestParam String q) {
        List<Customer> customerList = customerService.searchCustomer(q);
        List<CustomerResponseDto> responseList = customerList.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    private CustomerResponseDto toResponseDto(Customer customer) {
        if (customer == null) return null;
        CustomerResponseDto dto = new CustomerResponseDto();
        dto.setId(customer.getId());
        dto.setTitle(customer.getTitle());
        dto.setName(customer.getName());
        dto.setDob(customer.getDob());
        dto.setSalary(customer.getSalary());
        dto.setAddress(customer.getAddress());
        dto.setCity(customer.getCity());
        dto.setProvince(customer.getProvince());
        dto.setPostalCode(customer.getPostalCode());
        return dto;
    }
}
