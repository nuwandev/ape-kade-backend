package com.nuwandev.pos.controller;

import com.nuwandev.pos.model.dto.request.ItemRequestDto;
import com.nuwandev.pos.model.dto.response.ApiResponse;
import com.nuwandev.pos.model.dto.response.ItemResponseDto;
import com.nuwandev.pos.model.dto.response.PageResponse;
import com.nuwandev.pos.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ItemResponseDto>>> getItems(
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "created_at") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        PageResponse<ItemResponseDto> data = itemService.getItems(q, page, size, sortBy, direction);
        return ResponseEntity.ok(
                ApiResponse.<PageResponse<ItemResponseDto>>builder()
                        .success(true)
                        .message("Terminal inventory synchronized.")
                        .data(data)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ItemResponseDto>> createItem(@Valid @RequestBody ItemRequestDto itemRequest) {
        ItemResponseDto createdItem = itemService.createItem(itemRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<ItemResponseDto>builder()
                        .success(true)
                        .message("New stock item initialized.")
                        .data(createdItem)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ItemResponseDto>> updateItem(
            @PathVariable String id,
            @Valid @RequestBody ItemRequestDto itemRequest
    ) {
        ItemResponseDto updatedItem = itemService.updateItem(id, itemRequest);
        return ResponseEntity.ok(
                ApiResponse.<ItemResponseDto>builder()
                        .success(true)
                        .message("Item parameters updated.")
                        .data(updatedItem)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteItem(@PathVariable String id) {
        itemService.deleteItem(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Item purged from terminal.")
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}