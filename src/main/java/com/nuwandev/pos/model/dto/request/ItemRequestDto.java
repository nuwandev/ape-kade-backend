package com.nuwandev.pos.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestDto {
    private String sku;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer currentStock;
    private Integer alertLevel;
    private UUID categoryId;
}
