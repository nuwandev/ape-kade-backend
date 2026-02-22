package com.nuwandev.pos.repository.impl;

import com.nuwandev.pos.model.Item;
import com.nuwandev.pos.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {

    private final JdbcTemplate jdbc;

    private static final List<String> ALLOWED_SORT_COLUMNS = List.of(
            "id", "sku", "name", "price", "current_stock", "alert_level", "category_id", "created_at", "updated_at"
    );

    private String validateSortBy(String sortBy) {
        if (sortBy == null || sortBy.isEmpty()) return "created_at";
        return ALLOWED_SORT_COLUMNS.contains(sortBy) ? sortBy : "created_at";
    }

    private Item mapRow(ResultSet rs) throws SQLException {
        Item item = new Item();
        item.setId(UUID.fromString(rs.getString("id_str")));
        item.setSku(rs.getString("sku"));
        item.setName(rs.getString("name"));
        item.setDescription(rs.getString("description"));
        item.setPrice(rs.getBigDecimal("price"));
        item.setCategoryId(UUID.fromString(rs.getString("cat_id_str")));
        item.setCreatedAt(rs.getTimestamp("created_at"));
        item.setCurrentStock(rs.getInt("current_stock"));
        item.setAlertLevel(rs.getInt("alert_level"));
        item.setUpdatedAt(rs.getTimestamp("updated_at"));
        return item;
    }

    @Override
    public List<Item> findAll(int page, int size, String sortBy, String direction) {
        int offset = page * size;
        String orderBy = validateSortBy(sortBy);
        String dir = (direction != null && direction.equalsIgnoreCase("desc")) ? "DESC" : "ASC";
        String sql = """
            SELECT BIN_TO_UUID(i.id) as id_str,
                   i.sku,
                   i.name,
                   i.description,
                   i.price,
                   BIN_TO_UUID(i.category_id) as cat_id_str,
                   i.created_at,
                   i.current_stock,
                   i.alert_level,
                   i.updated_at,
                   c.display_name as cat_display_name,
                   c.tagline as cat_tagline,
                   c.slug as cat_slug,
                   c.visibility as cat_visibility,
                   c.icon as cat_icon,
                   c.seo_description as cat_seo_description,
                   c.created_at as cat_created_at
            FROM item i
            LEFT JOIN category c ON i.category_id = c.id
            ORDER BY """ + orderBy + " " + dir + " LIMIT ? OFFSET ?";
        return jdbc.query(sql, (rs, rowNum) -> mapRow(rs), size, offset);
    }

    @Override
    public Item save(Item item) {
        String sql = """
                INSERT INTO item (
                                  id,
                                  sku,
                                  name,
                                  description,
                                  price,
                                  current_stock,
                                  alert_level,
                                  category_id,
                                  created_at,
                                  updated_at
                                  )
                VALUES (UUID_TO_BIN(?), ?, ?, ?, ?, ?, ?, UUID_TO_BIN(?), ?, ?)
                """;
        jdbc.update(sql,
                item.getId().toString(),
                item.getSku(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getCurrentStock(),
                item.getAlertLevel(),
                item.getCategoryId().toString(),
                item.getCreatedAt(),
                item.getUpdatedAt()
        );
        return item;
    }

    @Override
    public Optional<Item> findById(UUID id) {
        String sql = """
            SELECT BIN_TO_UUID(i.id) as id_str,
                   i.sku,
                   i.name,
                   i.description,
                   i.price,
                   BIN_TO_UUID(i.category_id) as cat_id_str,
                   i.created_at,
                   i.current_stock,
                   i.alert_level,
                   i.updated_at,
                   c.display_name as cat_display_name,
                   c.tagline as cat_tagline,
                   c.slug as cat_slug,
                   c.visibility as cat_visibility,
                   c.icon as cat_icon,
                   c.seo_description as cat_seo_description,
                   c.created_at as cat_created_at
            FROM item i
            LEFT JOIN category c ON i.category_id = c.id
            WHERE i.id = UUID_TO_BIN(?)
        """;
        List<Item> items = jdbc.query(sql, (rs, rowNum) -> mapRow(rs), id.toString());
        return items.isEmpty() ? Optional.empty() : Optional.of(items.get(0));
    }

    @Override
    public Item update(Item item) {
        String sql = "UPDATE item SET sku = ?, name = ?, description = ?, price = ?, current_stock = ?, alert_level = ?, category_id = UUID_TO_BIN(?), updated_at = ? WHERE id = UUID_TO_BIN(?)";
        jdbc.update(sql,
                item.getSku(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getCurrentStock(),
                item.getAlertLevel(),
                item.getCategoryId().toString(),
                item.getUpdatedAt(),
                item.getId().toString()
        );
        return item;
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "DELETE FROM item WHERE id = UUID_TO_BIN(?)";
        jdbc.update(sql, id.toString());
    }

    @Override
    public long countItems() {
        String sql = "SELECT COUNT(*) FROM item";
        Long count = jdbc.queryForObject(sql, Long.class);
        return count != null ? count : 0L;
    }
}