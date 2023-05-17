package com.jiltsa.admin.seller.controller;

import com.jiltsa.admin.seller.domain.dto.SellerDto;
import com.jiltsa.admin.seller.domain.service.SellerDService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("jiltsa/api/v1/sellers")
@RequiredArgsConstructor
@CrossOrigin
public class SellerController {
    private final SellerDService service;

    @GetMapping
    public List<SellerDto> getSellers(){
        return service.getAllSellers();
    }
    @GetMapping("/branch/{branchId}")
    public List<SellerDto> getSellersByBranch(@PathVariable("branchId") Integer id){
        return service.getSellersByBranch(id);
    }
    @PostMapping
    public SellerDto newSeller(@RequestBody SellerDto sellerDto){
        return service.newSeller(sellerDto);
    }

    @PatchMapping("/{sellerId}")
    public SellerDto disableSeller(@PathVariable("sellerId") Integer id){
        return service.disableSeller(id);
    }

    @GetMapping("/{sellerId}")
    public Optional<SellerDto> getSeller(@PathVariable("sellerId") Integer id){
        return service.getSeller(id);
    }

}
