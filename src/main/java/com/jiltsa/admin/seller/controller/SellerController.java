package com.jiltsa.admin.seller.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.seller.domain.dto.SellerDto;
import com.jiltsa.admin.seller.domain.service.SellerService;
import com.jiltsa.admin.common.exception.ResourceNotFoundException;
import com.jiltsa.admin.security.AdminOnly;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jiltsa/api/v1/sellers")
@RequiredArgsConstructor
@CrossOrigin
public class SellerController {
    private final SellerService service;

    @GetMapping
    public List<SellerDto> getSellers(){
        return service.getAllSellers();
    }
    @GetMapping("/branch/{branchId}")
    public List<SellerDto> getSellersByBranch(@PathVariable("branchId") Integer id){
        return service.getSellersByBranch(id);
    }
    @AdminOnly
    @PostMapping
    public SellerDto createSeller(@Valid @RequestBody SellerDto sellerDto){
        return service.createSeller(sellerDto);
    }

    @AdminOnly
    @PatchMapping("/{sellerId}")
    public SellerDto disableSeller(@PathVariable("sellerId") Integer id){
        return service.disableSeller(id);
    }

    @GetMapping("/{sellerId}")
    public SellerDto getSeller(@PathVariable("sellerId") Integer id){
        return service.getSeller(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seller", id));
    }

}
