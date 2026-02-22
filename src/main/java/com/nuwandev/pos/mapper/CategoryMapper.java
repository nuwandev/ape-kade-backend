package com.nuwandev.pos.mapper;

import com.nuwandev.pos.model.Category;
import com.nuwandev.pos.model.dto.request.CategoryRequestDto;
import com.nuwandev.pos.model.dto.response.CategoryResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryRequestDto requestDto);

    CategoryResponseDto toDto(Category category);

    List<CategoryResponseDto> toDtoList(List<Category> categories);
}
