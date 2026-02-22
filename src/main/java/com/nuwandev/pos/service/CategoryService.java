package com.nuwandev.pos.service;

import com.nuwandev.pos.model.dto.request.CategoryRequestDto;
import com.nuwandev.pos.model.dto.response.CategoryResponseDto;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<CategoryResponseDto> getAllCategories();

    CategoryResponseDto createCategory(CategoryRequestDto requestDto);

    CategoryResponseDto updateCategory(UUID id, CategoryRequestDto requestDto);

    void deleteCategory(UUID id);

    Boolean isSlugAvailable(String slug);
}
