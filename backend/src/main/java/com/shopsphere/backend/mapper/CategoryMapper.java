package com.shopsphere.backend.mapper;

import com.shopsphere.backend.dto.CategoryDTO;
import com.shopsphere.backend.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDTO toDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}