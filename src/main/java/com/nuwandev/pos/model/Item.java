package com.nuwandev.pos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private UUID id;
    private String sku;
    private String name;
    private String description;
    private BigDecimal price;
    private UUID categoryId;
    private Timestamp createdAt;
    private Integer currentStock;
    private Integer alertLevel;
    private Timestamp updatedAt;
}
