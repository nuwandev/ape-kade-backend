package com.nuwandev.pos.service;

import com.nuwandev.pos.model.dto.response.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    List<CategoryResponseDto> getAllCategories();
}
