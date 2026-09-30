package com.jiltsa.admin.seller.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.seller.domain.dto.SellerDto;
import com.jiltsa.admin.seller.domain.service.SellerService;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import com.jiltsa.admin.security.AdminOnly;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/sellers")
@RequiredArgsConstructor
@CrossOrigin
public class SellerController {
    private final SellerService service;

    @Operation(summary = "List all sellers")
    @GetMapping
    public List<SellerDto> getSellers(){
        return service.getAllSellers();
    }
    @Operation(summary = "List a branch's sellers")
    @GetMapping("/branch/{branchId}")
    public List<SellerDto> getSellersByBranch(@Parameter(description = "Branch id") @PathVariable("branchId") Integer id){
        return service.getSellersByBranch(id);
    }
    @Operation(summary = "Create a seller")
    @AdminOnly
    @PostMapping
    public SellerDto createSeller(@Valid @RequestBody SellerDto sellerDto){
        return service.createSeller(sellerDto);
    }

    @Operation(summary = "Disable a seller")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No seller with that id")
    @AdminOnly
    @PatchMapping("/{sellerId}")
    public SellerDto disableSeller(@Parameter(description = "Seller id") @PathVariable("sellerId") Integer id){
        return service.disableSeller(id);
    }

    @Operation(summary = "Get a seller")
    @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "404", description = "No seller with that id")
    @GetMapping("/{sellerId}")
    public SellerDto getSeller(@Parameter(description = "Seller id") @PathVariable("sellerId") Integer id){
        return service.getSeller(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seller", id));
    }

}
