package com.nuwandev.pos.repository;

import com.nuwandev.pos.model.Category;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAllWithCount();
}
