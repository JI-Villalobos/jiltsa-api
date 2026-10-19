package com.jiltsa.admin.sales.controller;

import com.jiltsa.admin.sales.domain.dto.CreateProductDto;
import com.jiltsa.admin.sales.domain.dto.ProductBatchResultDto;
import com.jiltsa.admin.sales.domain.dto.ProductDto;
import com.jiltsa.admin.sales.domain.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Upload a batch of products", description = "All or nothing, at most 5000 products. A key may appear once per branch.")
    @ApiResponse(responseCode = "409", description = "A key repeats within the batch or already exists for its branch")
    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductBatchResultDto createProducts(
            @RequestBody @NotEmpty @Size(max = MAX_BATCH_SIZE) List<@Valid @NotNull CreateProductDto> products){
        return service.createProducts(products);
    }

    @Operation(summary = "List a branch's product catalog", description = "Ordered by key. Empty if the branch has no products.")
    @GetMapping("/branch/{branchId}")
    public List<ProductDto> getProductsByBranch(@Parameter(description = "Branch id") @PathVariable("branchId") Integer branchId){
        return service.getByBranch(branchId);
    }

    /**
     * Meant for labeling a set of sales: send the keys of the sales and get back only the
     * matching products, instead of downloading the whole catalog or asking key by key.
     */
    @Operation(summary = "Find a branch's products by key",
            description = "At most 5000 keys; repeated keys are ignored. Keys without a product are left out of the result.")
    @PostMapping("/branch/{branchId}/lookup")
    public List<ProductDto> findProductsByKeys(
            @Parameter(description = "Branch id") @PathVariable("branchId") Integer branchId,
            @RequestBody @NotEmpty @Size(max = MAX_BATCH_SIZE) List<@NotBlank String> keys){
        return service.getByBranchAndKeys(branchId, keys);
    }
}
