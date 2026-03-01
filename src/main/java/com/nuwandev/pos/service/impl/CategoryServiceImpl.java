package com.nuwandev.pos.service.impl;

import com.nuwandev.pos.exception.DuplicateResourceException;
import com.nuwandev.pos.exception.ResourceNotFoundException;
import com.nuwandev.pos.mapper.CategoryMapper;
import com.nuwandev.pos.model.Category;
import com.nuwandev.pos.model.dto.request.CategoryRequestDto;
import com.nuwandev.pos.model.dto.response.CategoryResponseDto;
import com.nuwandev.pos.repository.CategoryRepository;
import com.nuwandev.pos.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
        if (!categoryRepository.isSlugAvailable(requestDto.getSlug(), null)) {
            throw new DuplicateResourceException("The URL slug '" + requestDto.getSlug() + "' is already taken.");
        }

        Category category = mapper.toEntity(requestDto);
        category.setId(UUID.randomUUID());
        category.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        category.setItemCount(0);

        Category savedCategory = categoryRepository.save(category);
        return mapper.toDto(savedCategory);
    }

    @Override
    public CategoryResponseDto updateCategory(UUID id, CategoryRequestDto requestDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category could not find ID: " + id));

        if (!categoryRepository.isSlugAvailable(requestDto.getSlug(), id)) {
            throw new DuplicateResourceException("Cannot update: Slug '" + requestDto.getSlug() + "' is used by another category.");
        }

        category.setDisplayName(requestDto.getDisplayName());
        category.setTagline(requestDto.getTagline());
        category.setSlug(requestDto.getSlug());
        category.setVisibility(requestDto.getVisibility());
        category.setIcon(requestDto.getIcon());
        category.setSeoDescription(requestDto.getSeoDescription());

        Category updatedCategory = categoryRepository.update(category);
        return mapper.toDto(updatedCategory);
    }

    @Override
    public void deleteCategory(UUID id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete: Category " + id + " does not exist.");
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public Boolean isSlugAvailable(String slug, UUID excludeId) {
        return categoryRepository.isSlugAvailable(slug, excludeId);
    }
}
