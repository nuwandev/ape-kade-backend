package com.nuwandev.pos.controller;

import com.nuwandev.pos.model.dto.request.ItemRequestDto;
import com.nuwandev.pos.model.dto.response.ApiResponse;
import com.nuwandev.pos.model.dto.response.ItemResponseDto;
import com.nuwandev.pos.model.dto.response.PageResponse;
import com.nuwandev.pos.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ItemResponseDto>>> getItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "created_at") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        PageResponse<ItemResponseDto> data = itemService.getItems(page, size, sortBy, direction);
        return ResponseEntity.ok(ApiResponse.success("Items retrieved successfully", data));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ItemResponseDto>> createItem(@RequestBody ItemRequestDto itemRequest) {
        ItemResponseDto createdItem = itemService.createItem(itemRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Item created successfully", createdItem));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ItemResponseDto>> updateItem(@PathVariable String id, @RequestBody ItemRequestDto itemRequest) {
        ItemResponseDto updatedItem = itemService.updateItem(id, itemRequest);
        return ResponseEntity.ok(ApiResponse.success("Item updated successfully", updatedItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteItem(@PathVariable String id) {
        itemService.deleteItem(id);
        return ResponseEntity.ok(ApiResponse.success("Item deleted successfully"));
    }
}