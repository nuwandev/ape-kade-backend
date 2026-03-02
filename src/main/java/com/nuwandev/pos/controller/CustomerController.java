package com.nuwandev.pos.controller;

import com.nuwandev.pos.model.dto.request.CustomerRequestDto;
import com.nuwandev.pos.model.dto.response.ApiResponse;
import com.nuwandev.pos.model.dto.response.CustomerResponseDto;
import com.nuwandev.pos.model.dto.response.PageResponse;
import com.nuwandev.pos.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<CustomerResponseDto>>> getCustomers(
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "created_at") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        PageResponse<CustomerResponseDto> data = customerService.getCustomers(q, page, size, sortBy, direction);
        return ResponseEntity.ok(ApiResponse.success("Customers retrieved successfully", data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponseDto>> getCustomerById(@PathVariable UUID id) {
        CustomerResponseDto customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(ApiResponse.success("Customer found", customer));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> saveCustomer(@Valid @RequestBody CustomerRequestDto requestDto) {
        customerService.saveCustomer(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Customer saved successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateCustomer(@PathVariable UUID id, @RequestBody CustomerRequestDto requestDto) {
        customerService.updateCustomer(id, requestDto);
        return ResponseEntity.ok(ApiResponse.success("Customer updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomerById(@PathVariable UUID id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.ok(ApiResponse.success("Customer deleted successfully"));
    }
}