package com.jiltsa.admin.sales.controller;

import com.jiltsa.admin.sales.domain.dto.CreateProductDto;
import com.jiltsa.admin.sales.domain.dto.ProductBatchResultDto;
import com.jiltsa.admin.sales.domain.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    static final int MAX_BATCH_SIZE = 5000;

    private final ProductService service;

    /** Products arrive in batches (a catalog export); a key must be new for its branch. */
    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductBatchResultDto createProducts(
            @RequestBody @NotEmpty @Size(max = MAX_BATCH_SIZE) List<@Valid @NotNull CreateProductDto> products){
        return service.createProducts(products);
    }
}
