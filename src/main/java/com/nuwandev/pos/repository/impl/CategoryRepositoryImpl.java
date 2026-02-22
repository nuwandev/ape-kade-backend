package com.nuwandev.pos.repository.impl;

import com.nuwandev.pos.model.Category;
import com.nuwandev.pos.model.enums.CategoryVisibility;
import com.nuwandev.pos.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {
    private final JdbcTemplate jdbc;

    @Override
    public Optional<Category> findById(UUID id) {
        String sql = """
                    SELECT BIN_TO_UUID(id) as id_str,
                           display_name,
                           tagline,
                           slug,
                           visibility,
                           icon,
                           seo_description,
                           created_at
                    FROM category
                    WHERE id = UUID_TO_BIN(?)
                """;
        return jdbc.query(sql, new Object[]{id.toString()}, rs -> {
            if (rs.next()) {
                Category category = new Category();
                category.setId(UUID.fromString(rs.getString("id_str")));
                category.setDisplayName(rs.getString("display_name"));
                category.setTagline(rs.getString("tagline"));
                category.setSlug(rs.getString("slug"));
                category.setVisibility(CategoryVisibility.valueOf(rs.getString("visibility")));
                category.setIcon(rs.getString("icon"));
                category.setSeoDescription(rs.getString("seo_description"));
                category.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                return Optional.of(category);
            } else {
                return Optional.empty();
            }
        });
    }

    @Override
    public Category update(Category category) {
        String sql = """
                    UPDATE category
                    SET display_name = ?, tagline = ?, slug = ?, visibility = ?, icon = ?, seo_description = ?
                    WHERE id = UUID_TO_BIN(?)
                """;
        boolean isUpdated = jdbc.update(
                sql,
                category.getDisplayName(),
                category.getTagline(),
                category.getSlug(),
                category.getVisibility().name(),
                category.getIcon(),
                category.getSeoDescription(),
                category.getId().toString()
        ) > 0;
        if (isUpdated) {
            category.setItemCount(findItemCount(category.getId()));
            return category;
        } else {
            throw new RuntimeException("Failed to update category");
        }
    }

    private Integer findItemCount(UUID id) {
        String sql = "SELECT COUNT(*) FROM item WHERE category_id = UUID_TO_BIN(?)";
        return jdbc.queryForObject(sql, new Object[]{id.toString()}, Integer.class);
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "DELETE FROM category WHERE id = UUID_TO_BIN(?)";
        boolean isDeleted = jdbc.update(sql, id.toString()) > 0;
        if (!isDeleted) {
            throw new RuntimeException("Failed to delete category");
        }
    }

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

    @Override
    public Category save(Category category) {
        String sql = """
                    INSERT INTO category(id, display_name, tagline, slug, visibility, icon, seo_description, created_at)
                    VALUES (UUID_TO_BIN(?), ?, ?, ?, ?, ?, ?, ?)
                """;
        boolean isSaved = jdbc.update(
                sql,
                category.getId().toString(),
                category.getDisplayName(),
                category.getTagline(),
                category.getSlug(),
                category.getVisibility().name(),
                category.getIcon(),
                category.getSeoDescription(),
                category.getCreatedAt()
        ) > 0;
        if (isSaved) {
            return category;
        } else {
            throw new RuntimeException("Failed to save category");
        }
    }
}