package com.jiltsa.admin.sales.domain.service;

import com.jiltsa.admin.common.exception.ConflictException;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import com.jiltsa.admin.sales.domain.dto.CategoryBatchResultDto;
import com.jiltsa.admin.sales.domain.dto.CategoryDto;
import com.jiltsa.admin.sales.persistence.entity.Category;
import com.jiltsa.admin.sales.persistence.mapper.CategoryMapper;
import com.jiltsa.admin.sales.persistence.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * A category name may be stored once, ignoring case (the column's collation does the same).
 * The unique index on category_name backs the checks up against concurrent requests.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public List<CategoryDto> getCategories() {
        return mapper.toCategoryDtoList(repository.findAllByOrderByCategoryName());
    }

    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        rejectExisting(List.of(categoryDto.categoryName()), null);
        return mapper.toCategoryDto(repository.save(mapper.toCategory(categoryDto)));
    }

    /** All or nothing: rejected if a name repeats within the batch or already exists. */
    @Transactional
    public CategoryBatchResultDto createCategories(List<CategoryDto> categories) {
        Set<String> seen = new HashSet<>();
        List<String> repeated = categories.stream()
                .map(CategoryDto::categoryName)
                .filter(name -> !seen.add(name.toLowerCase(Locale.ROOT)))
                .toList();
        if (!repeated.isEmpty()) {
            throw new ConflictException("Categories repeated within the batch: " + String.join(", ", repeated));
        }
        rejectExisting(categories.stream().map(CategoryDto::categoryName).toList(), null);

        repository.saveAll(mapper.toCategoryList(categories));
        return new CategoryBatchResultDto(categories.size());
    }

    @Transactional
    public CategoryDto updateCategory(Integer id, CategoryDto categoryDto) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));
        rejectExisting(List.of(categoryDto.categoryName()), id);
        category.setCategoryName(categoryDto.categoryName());
        return mapper.toCategoryDto(repository.saveAndFlush(category));
    }

    /** Products keep their category as text, so deleting a category leaves them untouched. */
    @Transactional
    public void deleteCategory(Integer id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));
        repository.delete(category);
    }

    private void rejectExisting(Collection<String> names, Integer exceptId) {
        List<String> existing = repository.findByCategoryNameIn(names).stream()
                .filter(category -> !category.getId().equals(exceptId))
                .map(Category::getCategoryName)
                .toList();
        if (!existing.isEmpty()) {
            throw new ConflictException("Categories already exist: " + String.join(", ", existing));
        }
    }
}
