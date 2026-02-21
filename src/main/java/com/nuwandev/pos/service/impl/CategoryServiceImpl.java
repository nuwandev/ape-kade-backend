package com.nuwandev.pos.service.impl;

import com.nuwandev.pos.mapper.CategoryMapper;
import com.nuwandev.pos.model.dto.response.CategoryResponseDto;
import com.nuwandev.pos.repository.CategoryRepository;
import com.nuwandev.pos.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        return mapper.toDtoList(categoryRepository.findAllWithCount());
    }
}
