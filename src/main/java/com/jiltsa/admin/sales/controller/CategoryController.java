package com.jiltsa.admin.sales.controller;

import com.jiltsa.admin.sales.domain.dto.CategoryBatchResultDto;
import com.jiltsa.admin.sales.domain.dto.CategoryDto;
import com.jiltsa.admin.sales.domain.service.CategoryService;
import com.jiltsa.admin.security.AdminOnly;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    static final int MAX_BATCH_SIZE = 500;

    private final CategoryService service;

    @Operation(summary = "List categories", description = "Ordered by name.")
    @GetMapping
    public List<CategoryDto> getCategories(){
        return service.getCategories();
    }

    @Operation(summary = "Create a category")
    @ApiResponse(responseCode = "201", description = "Created", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "409", description = "A category with that name already exists")
    @AdminOnly
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@Valid @RequestBody CategoryDto categoryDto){
        return service.createCategory(categoryDto);
    }

    @Operation(summary = "Create a batch of categories", description = "All or nothing, at most 500 categories. Names are compared ignoring case.")
    @ApiResponse(responseCode = "201", description = "Created", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "409", description = "A name repeats within the batch or already exists")
    @AdminOnly
    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryBatchResultDto createCategories(
            @RequestBody @NotEmpty @Size(max = MAX_BATCH_SIZE) List<@Valid @NotNull CategoryDto> categories){
        return service.createCategories(categories);
    }

    @Operation(summary = "Rename a category")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No category with that id")
    @ApiResponse(responseCode = "409", description = "Another category already has that name")
    @AdminOnly
    @PutMapping("/{categoryId}")
    public CategoryDto updateCategory(
            @Parameter(description = "Category id") @PathVariable("categoryId") Integer categoryId,
            @Valid @RequestBody CategoryDto categoryDto){
        return service.updateCategory(categoryId, categoryDto);
    }

    @Operation(summary = "Delete a category", description = "Products keep their category text.")
    @ApiResponse(responseCode = "204", description = "Deleted")
    @ApiResponse(responseCode = "404", description = "No category with that id")
    @AdminOnly
    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@Parameter(description = "Category id") @PathVariable("categoryId") Integer categoryId){
        service.deleteCategory(categoryId);
    }
}
