package com.jiltsa.admin.sales.persistence.mapper;

import com.jiltsa.admin.sales.domain.dto.CategoryDto;
import com.jiltsa.admin.sales.persistence.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toCategoryDto(Category category);
    List<CategoryDto> toCategoryDtoList(List<Category> categories);

    @Mapping(target = "id", ignore = true)
    Category toCategory(CategoryDto categoryDto);
    List<Category> toCategoryList(List<CategoryDto> categoryDtos);
}
