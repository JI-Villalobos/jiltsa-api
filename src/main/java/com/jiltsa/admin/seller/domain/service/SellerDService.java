package com.jiltsa.admin.seller.domain.service;

import com.jiltsa.admin.seller.domain.dto.SellerDto;
import com.jiltsa.admin.seller.domain.repository.SellerDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SellerDService {
    private final SellerDRepository sellerDRepository;

    public List<SellerDto> getAllSellers(){
        return  sellerDRepository.getAllSellers();
    }
    public List<SellerDto> getSellersByBranch(Integer branchId){
        return sellerDRepository.getSellersByBranch(branchId);
    }

    @Transactional
    public SellerDto newSeller(SellerDto sellerDto){
        return sellerDRepository.newSeller(sellerDto);
    }

    @Transactional
    public SellerDto disableSeller(Integer sellerId){
        return sellerDRepository.disableSeller(sellerId);
    }

    public Optional<SellerDto> getSeller(Integer sellerId){
        return sellerDRepository.getSeller(sellerId);
    }
}
