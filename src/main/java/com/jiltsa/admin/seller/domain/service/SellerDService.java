package com.jiltsa.admin.seller.domain.service;

import com.jiltsa.admin.seller.domain.dto.SellerDto;
import com.jiltsa.admin.seller.domain.repository.SellerDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerDService {
    private final SellerDRepository sellerDRepository;

    public List<SellerDto> getAllSellers(){
        return  sellerDRepository.getAllSellers();
    }

    public SellerDto newSeller(SellerDto sellerDto){
        return sellerDRepository.newSeller(sellerDto);
    }

    public SellerDto disableSeller(Integer sellerId){
        return sellerDRepository.disableSeller(sellerId);
    }

    public Optional<SellerDto> getSeller(Integer sellerId){
        return sellerDRepository.getSeller(sellerId);
    }
}
