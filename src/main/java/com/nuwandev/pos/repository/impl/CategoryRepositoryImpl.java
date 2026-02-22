package com.nuwandev.pos.repository.impl;

import com.nuwandev.pos.model.Category;
import com.nuwandev.pos.model.enums.CategoryVisibility;
import com.nuwandev.pos.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final JdbcTemplate jdbc;

    public List<Category> findAllWithCount() {
        String sql = """
                    SELECT BIN_TO_UUID(c.id) as id_str,
                           c.display_name,
                           c.tagline,
                           c.slug,
                           c.visibility,
                           c.icon,
                           c.seo_description,
                           c.created_at,
                           COUNT(i.id) as item_count
                    FROM category c
                    LEFT JOIN item i ON c.id = i.category_id
                    GROUP BY c.id
                """;
        return jdbc.query(sql, (rs, rowNum) -> {
            Category category = new Category();
            category.setId(UUID.fromString(rs.getString("id_str")));
            category.setDisplayName(rs.getString("display_name"));
            category.setTagline(rs.getString("tagline"));
            category.setSlug(rs.getString("slug"));
            category.setVisibility(CategoryVisibility.valueOf(rs.getString("visibility")));
            category.setIcon(rs.getString("icon"));
            category.setSeoDescription(rs.getString("seo_description"));
            category.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            category.setItemCount(rs.getInt("item_count"));

            return category;
        });
    }
}