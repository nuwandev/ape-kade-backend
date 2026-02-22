package com.nuwandev.pos.model.dto.request;

import com.nuwandev.pos.model.enums.CategoryVisibility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestDto {
    private String displayName;
    private String tagline;
    private String slug;
    private CategoryVisibility visibility;
    private String icon;
    private String seoDescription;
}
