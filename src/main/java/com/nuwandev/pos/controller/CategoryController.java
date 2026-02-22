package com.nuwandev.pos.controller;

import com.nuwandev.pos.model.dto.request.CategoryRequestDto;
import com.nuwandev.pos.model.dto.response.CategoryResponseDto;
import com.nuwandev.pos.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        List<CategoryResponseDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody CategoryRequestDto requestDto) {
        CategoryResponseDto savedCategory = categoryService.createCategory(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable UUID id, @RequestBody CategoryRequestDto requestDto) {
        CategoryResponseDto updatedCategory = categoryService.updateCategory(id, requestDto);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check-slug/{slug}")
    public ResponseEntity<Boolean> isSlugAvailable(@PathVariable String slug) {
        return ResponseEntity.ok(categoryService.isSlugAvailable(slug));
    }
}
