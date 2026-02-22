package com.nuwandev.pos.repository;

import com.nuwandev.pos.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository {
    List<Category> findAllWithCount();

    Category save(Category category);

    Optional<Category> findById(UUID id);

    Category update(Category category);

    void deleteById(UUID id);
}
