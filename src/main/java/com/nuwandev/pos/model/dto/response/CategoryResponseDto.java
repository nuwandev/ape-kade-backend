package com.nuwandev.pos.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nuwandev.pos.model.enums.CategoryVisibility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResponseDto {
    private UUID id;
    private String displayName;
    private String tagline;
    private String slug;
    private CategoryVisibility visibility;
    private String icon;
    private String seoDescription;
    private LocalDateTime createdAt;
    private Integer itemCount;
}
