package com.nuwandev.pos.service.impl;

import com.nuwandev.pos.mapper.CategoryMapper;
import com.nuwandev.pos.model.Category;
import com.nuwandev.pos.model.dto.request.CategoryRequestDto;
import com.nuwandev.pos.model.dto.response.CategoryResponseDto;
import com.nuwandev.pos.repository.CategoryRepository;
import com.nuwandev.pos.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAllWithCount();
        return mapper.toDtoList(categories);
    }

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto requestDto) {
        Category category = mapper.toEntity(requestDto);
        category.setId(UUID.randomUUID());
        category.setCreatedAt(LocalDateTime.now());
        category.setItemCount(0);

        Category savedCategory = categoryRepository.save(category);
        return mapper.toDto(savedCategory);
    }

    @Override
    public CategoryResponseDto updateCategory(UUID id, CategoryRequestDto requestDto) {
        return null;
    }

    @Override
    public void deleteCategory(UUID id) {

    }
}
